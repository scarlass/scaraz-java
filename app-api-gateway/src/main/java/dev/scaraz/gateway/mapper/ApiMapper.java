package dev.scaraz.gateway.mapper;

import dev.scaraz.common.dto.response.api.ApiEntryDTO;
import dev.scaraz.common.dto.response.api.ApiRouteDTO;
import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiRoute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiMapper {

    public ApiRouteDTO toDTO(ApiRoute o) {
        if (o == null) return null;

        Long entryId = o.getEntry() != null ?
                       o.getEntry().getId() :
                       null;
        return ApiRouteDTO.builder()
                .id(o.getId())
                .entryId(entryId)
                .active(o.isActive())
                .method(o.getMethod())
                .path(o.getPath())
                .variables(Arrays.asList(o.getVariables()))
                .description(o.getDescription())
                .build();
    }

    public ApiEntryDTO toDTO(ApiEntry o) {
        if (o == null) return null;
        return ApiEntryDTO.builder()
                .id(o.getId())
                .name(o.getName())
                .prefix(o.getPrefix())
                .description(o.getDescription())
                .tags(Arrays.asList(o.getTags()))
                .routes(o.getRoutes().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

}
