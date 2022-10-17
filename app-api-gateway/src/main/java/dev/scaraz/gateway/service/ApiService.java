package dev.scaraz.gateway.service;

import dev.scaraz.common.dto.request.*;
import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiHost;
import dev.scaraz.gateway.entities.ApiRoute;
import org.springframework.transaction.annotation.Transactional;

public interface ApiService {
    void refresh();

    ApiEntry create(CreateApiEntryDTO dto);

    ApiEntry update(long entryId, UpdateApiEntryDTO dto);

    ApiEntry addHosts(ApiEntry entry, Iterable<CreateApiHostDTO> dtos);

    ApiEntry addHosts(long entryId, Iterable<CreateApiHostDTO> dtos);

    ApiHost updateHost(long hostId, UpdateApiHostDTO dto);

    @Transactional
    ApiEntry addRoutes(ApiEntry entry, Iterable<CreateApiRouteDTO> dtos);

    @Transactional
    ApiEntry addRoutes(long entryId, Iterable<CreateApiRouteDTO> dtos);

    ApiRoute updateRoute(long routeId, UpdateApiRouteDTO dto);
}
