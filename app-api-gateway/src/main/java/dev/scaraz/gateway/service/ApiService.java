package dev.scaraz.gateway.service;

import dev.scaraz.common.dto.request.CreateApiEntryReqDTO;
import dev.scaraz.common.dto.request.CreateApiRouteReqDTO;
import dev.scaraz.common.dto.request.UpdateApiEntryDTO;
import dev.scaraz.common.dto.request.UpdateApiRouteDTO;
import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiRoute;
import org.springframework.transaction.annotation.Transactional;

public interface ApiService {
    void refresh();

    ApiEntry create(CreateApiEntryReqDTO dto);

    ApiEntry update(long entryId, UpdateApiEntryDTO dto);

    @Transactional
    ApiEntry addRoutes(ApiEntry entry, Iterable<CreateApiRouteReqDTO> dtos);

    @Transactional
    ApiEntry addRoutes(long entryId, Iterable<CreateApiRouteReqDTO> dtos);

    ApiRoute updateRoute(long routeId, UpdateApiRouteDTO dto);
}
