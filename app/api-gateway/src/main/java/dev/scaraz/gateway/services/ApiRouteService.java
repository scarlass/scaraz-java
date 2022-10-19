package dev.scaraz.gateway.services;

import dev.scaraz.common.dto.response.api.ApiEntryDTO;

import java.util.List;

public interface ApiRouteService {
    List<ApiEntryDTO> getAllEntries(int retryAttempt);
}
