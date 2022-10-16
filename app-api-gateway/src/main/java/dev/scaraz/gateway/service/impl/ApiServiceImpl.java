package dev.scaraz.gateway.service.impl;

import dev.scaraz.common.dto.request.CreateApiEntryReqDTO;
import dev.scaraz.common.dto.request.CreateApiRouteReqDTO;
import dev.scaraz.common.dto.request.UpdateApiEntryDTO;
import dev.scaraz.common.dto.request.UpdateApiRouteDTO;
import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiRoute;
import dev.scaraz.gateway.repositories.ApiEntryRepo;
import dev.scaraz.gateway.repositories.ApiRouteRepo;
import dev.scaraz.gateway.service.ApiQueryService;
import dev.scaraz.gateway.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private static final String PATH_VARIABLE_PATTERN = "/(\\$\\{([\\w\\d_-]+)\\})(/)?";

    private final ApiEntryRepo entryRepo;
    private final ApiRouteRepo routeRepo;

    private final ApiQueryService queryService;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void refresh() {
        eventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    @Transactional
    public ApiEntry create(CreateApiEntryReqDTO dto) {
        ApiEntry entry = ApiEntry.builder()
                .name(dto.getName())
                .prefix(dto.getPrefix())
                .description(dto.getDescription())
                .build();

        entry.setTags(dto.getTags());

        entry = entryRepo.save(entry);
        return addRoutes(entry, dto.getRoutes());
    }

    @Override
    @Transactional
    public ApiEntry update(long entryId, UpdateApiEntryDTO dto) {
        ApiEntry entry = queryService.findEntryById(entryId);

        Optional.ofNullable(dto.getName())
                .ifPresent(entry::setName);

        Optional.ofNullable(dto.getPrefix())
                .ifPresent(prefix -> {
                    if (!prefix.startsWith("/")) prefix = "/" + prefix;
                    entry.setPrefix(prefix);
                });

        Optional.ofNullable(dto.getDescription())
                .ifPresent(entry::setDescription);

        Optional.ofNullable(dto.getTags())
                .ifPresent(entry::setTags);

        refresh();
        return entryRepo.save(entry);
    }

    @Override
    @Transactional
    public ApiEntry addRoutes(ApiEntry entry, Iterable<CreateApiRouteReqDTO> dtos) {
        List<ApiRoute> routes = new ArrayList<>();
        Pattern pattern = Pattern.compile(PATH_VARIABLE_PATTERN, Pattern.CASE_INSENSITIVE);

        for (CreateApiRouteReqDTO dto : dtos) {
            if (!dto.getPath().startsWith("/"))
                dto.setPath("/" + dto.getPath());

            ApiRoute route = ApiRoute.builder()
                    .entry(entry)
                    .path(dto.getPath())
                    .method(dto.getMethod())
                    .active(false)
                    .build();

            route.setVariables(getPathVariables(pattern, dto.getPath()));
            routes.add(route);
        }

        if (routes.size() > 0) {
            entry.getRoutes()
                    .addAll(routeRepo.saveAll(routes));
            refresh();
        }

        return entry;
    }

    @Override
    public ApiEntry addRoutes(long entryId, Iterable<CreateApiRouteReqDTO> dtos) {
        ApiEntry entry = entryRepo.findById(entryId)
                .orElseThrow();

        return addRoutes(entry, dtos);
    }

    @Override
    @Transactional
    public ApiRoute updateRoute(long routeId, UpdateApiRouteDTO dto) {
        ApiRoute route = queryService.findRouteById(routeId);

        Optional.ofNullable(dto.getActive())
                .ifPresent(route::setActive);

        Optional.ofNullable(dto.getDescription())
                .ifPresent(route::setDescription);

        Optional.ofNullable(dto.getMethod())
                .ifPresent(route::setMethod);

        if (!StringUtils.isBlank(dto.getPath())) {
            Pattern pattern = Pattern.compile(PATH_VARIABLE_PATTERN, Pattern.CASE_INSENSITIVE);
            List<String> vars = getPathVariables(pattern, dto.getPath());
            route.setVariables(vars);
        }

        route = routeRepo.save(route);
        refresh();
        return route;
    }

    private List<String> getPathVariables(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        List<MatchResult> results = matcher.results().collect(Collectors.toList());
        if (results.size() == 0) return new ArrayList<>();

        return results.stream()
                .map(matchResult -> matchResult.group(1))
                .collect(Collectors.toList());
    }
}