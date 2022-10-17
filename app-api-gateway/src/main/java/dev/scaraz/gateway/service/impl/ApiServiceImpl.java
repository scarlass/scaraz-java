package dev.scaraz.gateway.service.impl;

import dev.scaraz.common.configuration.MessageTranslator;
import dev.scaraz.common.dto.request.*;
import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiHost;
import dev.scaraz.gateway.entities.ApiRoute;
import dev.scaraz.gateway.repositories.ApiEntryRepo;
import dev.scaraz.gateway.repositories.ApiRouteRepo;
import dev.scaraz.gateway.repositories.api.ApiHostRepo;
import dev.scaraz.gateway.service.ApiQueryService;
import dev.scaraz.gateway.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
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
    private final ApiHostRepo hostRepo;
    private final ApiRouteRepo routeRepo;

    private final ApiQueryService queryService;

    private final ApplicationEventPublisher eventPublisher;
    private final MessageTranslator translator;

    @Override
    public void refresh() {
        eventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    @Transactional
    public ApiEntry create(CreateApiEntryDTO dto) {

        if (dto.getPrefix() != null) {
            if (!dto.getPrefix().startsWith("/"))
                dto.setPrefix("/" + dto.getPrefix());

            checkEntryPrefix(dto.getPrefix());
        }
        else {
            String name = dto.getName();
            dto.setPrefix(name.toLowerCase());
            Pattern.compile("([.\"'()$%#@!^&*,<>\\[\\]\\-])").matcher(name).results()
                    .forEach(match -> {
                        dto.setPrefix(dto.getPrefix()
                                .replace(match.group(1), "_"));
                    });

            dto.setPrefix("/" + String.join("", dto.getPrefix().split(" ")));
        }

        ApiEntry entry = ApiEntry.builder()
                .name(dto.getName())
                .prefix(dto.getPrefix())
                .description(dto.getDescription())
                .build();

        entry.setTags(dto.getTags());

        entry = entryRepo.save(entry);
        return addRoutes(entry, dto.getRoutes());
//        return entry;
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

        return entryRepo.save(entry);
    }


    @Override
    @Transactional
    public ApiEntry addHosts(ApiEntry entry, Iterable<CreateApiHostDTO> dtos) {
        List<ApiHost> hosts = new ArrayList<>();

        for (CreateApiHostDTO dto : dtos) {
            ApiHost host = ApiHost.builder()
                    .entry(entry)
                    .host(dto.getHost())
                    .profile(dto.getProfile())
                    .description(dto.getDescription())
                    .build();

            hosts.add(host);
        }

        if (hosts.size() > 0) {
            entry.getHosts()
                    .addAll(hostRepo.saveAll(hosts));
        }

        return entry;
    }

    @Override
    public ApiEntry addHosts(long entryId, Iterable<CreateApiHostDTO> dtos) {
        ApiEntry entry = queryService.findEntryById(entryId);
        return addHosts(entry, dtos);
    }

    @Override
    public ApiHost updateHost(long hostId, UpdateApiHostDTO dto) {
        ApiHost host = queryService.findHostById(hostId);

        Optional.ofNullable(dto.getHost())
                .ifPresent(host::setHost);

        Optional.ofNullable(dto.getProfile())
                .ifPresent(host::setProfile);

        Optional.ofNullable(dto.getDescription())
                .ifPresent(host::setDescription);

        host = hostRepo.save(host);
        return host;
    }


    @Override
    @Transactional
    public ApiEntry addRoutes(ApiEntry entry, Iterable<CreateApiRouteDTO> dtos) {
        List<ApiRoute> routes = new ArrayList<>();
        Pattern pattern = Pattern.compile(PATH_VARIABLE_PATTERN, Pattern.CASE_INSENSITIVE);

        for (CreateApiRouteDTO dto : dtos) {
            if (!dto.getPath().startsWith("/"))
                dto.setPath("/" + dto.getPath());

            checkRoutePath(entry.getId(), dto.getPath(), null);

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
    public ApiEntry addRoutes(long entryId, Iterable<CreateApiRouteDTO> dtos) {
        ApiEntry entry = queryService.findEntryById(entryId);
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
            checkRoutePath(route.getEntry().getId(), dto.getPath(), routeId);

            Pattern pattern = Pattern.compile(PATH_VARIABLE_PATTERN, Pattern.CASE_INSENSITIVE);
            List<String> vars = getPathVariables(pattern, dto.getPath());
            route.setVariables(vars);
        }

        route = routeRepo.save(route);
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

    private void checkEntryPrefix(String prefix) {
        boolean startWithApi = Pattern.compile("^/api", Pattern.CASE_INSENSITIVE)
                .matcher(prefix)
                .matches();

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        if (startWithApi) {
            throw HttpClientErrorException.create(
                    "Reserved prefix: \"/api\"",
                    HttpStatus.BAD_REQUEST,
                    translator.badRequestTitle(),
                    header,
                    null,
                    StandardCharsets.UTF_8
            );
        }
        else if (entryRepo.existsByPrefixContainsIgnoreCase(prefix)) {
            throw HttpClientErrorException.create(
                    "Duplicate prefix :" + prefix,
                    HttpStatus.BAD_REQUEST,
                    translator.badRequestTitle(),
                    header,
                    null,
                    StandardCharsets.UTF_8
            );
        }

    }

    private void checkRoutePath(long entryId, String path, @Nullable Long routeId) {
        boolean isDuplicatePath = routeId != null ?
                                  routeRepo.existsByEntryIdAndPathContainingIgnoreCaseAndIdIsNot(entryId, path, routeId) :
                                  routeRepo.existsByEntryIdAndPathContainingIgnoreCase(entryId, path);

        if (isDuplicatePath) throwBadRequest("Duplicate path: " + path);
    }

    private void throwBadRequest(String message) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        throw HttpClientErrorException.create(
                message,
                HttpStatus.BAD_REQUEST,
                translator.badRequestTitle(),
                header,
                null,
                StandardCharsets.UTF_8
        );
    }
}
