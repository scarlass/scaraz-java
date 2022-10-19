package dev.scaraz.core.mapper;

import dev.scaraz.common.dto.response.api.ApiEntryDTO;
import dev.scaraz.common.dto.response.api.ApiHostDTO;
import dev.scaraz.core.datasource.domains.ApiEntry;
import dev.scaraz.core.datasource.domains.ApiHost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApiMapper {

    public ApiEntryDTO toDTO(ApiEntry o) {
        if (o == null) return null;
        return ApiEntryDTO.builder()
                .id(o.getId())
                .name(o.getName())
                .prefix(o.getPrefix())
                .description(o.getDescription())
                .tags(new ArrayList<>(o.getTags()))
                .hosts(o.getHosts().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ApiHostDTO toDTO(ApiHost o) {
        if (o == null) return null;

        String entryId = o.getEntry() != null ?
                       o.getEntry().getId() :
                       null;

        return ApiHostDTO.builder()
                .id(o.getId())
                .entryId(entryId)
                .active(o.isActive())
                .host(o.getHost())
                .description(o.getDescription())
                .build();
    }

}
