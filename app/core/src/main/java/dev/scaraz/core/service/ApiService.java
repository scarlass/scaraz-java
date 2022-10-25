package dev.scaraz.core.service;

import dev.scaraz.common.dto.request.CreateApiEntryDTO;
import dev.scaraz.common.dto.request.CreateApiHostDTO;
import dev.scaraz.common.dto.request.UpdateApiEntryDTO;
import dev.scaraz.common.dto.request.UpdateApiHostDTO;
import dev.scaraz.core.datasource.domains.api.ApiEntry;
import dev.scaraz.core.datasource.domains.api.ApiHost;

public interface ApiService {
    ApiEntry create(CreateApiEntryDTO o);

    ApiEntry update(String entryId, UpdateApiEntryDTO o);

    ApiEntry createHosts(String entryId, Iterable<CreateApiHostDTO> createHosts);

    ApiEntry createHosts(ApiEntry entry, Iterable<CreateApiHostDTO> createHosts);

    ApiHost updateHost(String hostId, UpdateApiHostDTO o);
}
