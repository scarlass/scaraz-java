package dev.scaraz.core.service.impl;

import dev.scaraz.common.dto.request.CreateApiEntryDTO;
import dev.scaraz.common.dto.request.CreateApiHostDTO;
import dev.scaraz.common.dto.request.UpdateApiEntryDTO;
import dev.scaraz.common.dto.request.UpdateApiHostDTO;
import dev.scaraz.core.datasource.domains.ApiEntry;
import dev.scaraz.core.datasource.domains.ApiHost;
import dev.scaraz.core.datasource.repositories.ApiEntryRepo;
import dev.scaraz.core.datasource.repositories.ApiHostRepo;
import dev.scaraz.core.service.ApiService;
import dev.scaraz.core.web.query.ApiQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final ApiEntryRepo entryRepo;
    private final ApiHostRepo hostRepo;

    private final ApiQueryService queryService;

    @Override
    @Transactional
    public ApiEntry create(CreateApiEntryDTO o) {
        ApiEntry entry = ApiEntry.builder()
                .name(o.getName())
                .prefix(o.getPrefix())
                .description(o.getDescription())
                .tags(o.getTags())
                .build();

        if (entryRepo.existsByPrefixContainsIgnoreCase(o.getPrefix()))
            throw new IllegalStateException("Duplicate prefix, exist");

        return createHosts(entryRepo.save(entry), o.getHosts());
    }

    @Override
    @Transactional
    public ApiEntry update(String entryId, UpdateApiEntryDTO o) {
        ApiEntry entry = entryRepo.findById(entryId)
                .orElseThrow();

        Optional.ofNullable(o.getName())
                .ifPresent(entry::setName);

        Optional.ofNullable(o.getDescription())
                .ifPresent(entry::setDescription);

        Optional.ofNullable(o.getTags())
                .ifPresent(entry::setTags);

        if (o.getPrefix() != null) {
            if (entryRepo.existsByPrefixContainsIgnoreCase(o.getPrefix()))
                throw new IllegalStateException("Duplicate prefix, exist");
            entry.setPrefix(o.getPrefix());
        }

        return entryRepo.save(entry);
    }

    @Override
    public ApiEntry createHosts(String entryId, Iterable<CreateApiHostDTO> createHosts) {
        ApiEntry entry = entryRepo.findById(entryId)
                .orElseThrow();
        return createHosts(entry, createHosts);
    }

    @Override
    @Transactional
    public ApiEntry createHosts(ApiEntry entry, Iterable<CreateApiHostDTO> createHosts) {
        List<ApiHost> hosts = new ArrayList<>();

        for (CreateApiHostDTO dto : createHosts) {
            ApiHost host = ApiHost.builder()
                    .host(dto.getHost())
                    .active(true)
                    .description(dto.getDescription())
                    .entry(entry)
                    .build();

            hosts.add(host);
        }

        if (hosts.size() > 0)
            entry.setHosts(new HashSet<>(hostRepo.saveAll(hosts)));

        return entry;
    }

    @Override
    @Transactional
    public ApiHost updateHost(String hostId, UpdateApiHostDTO o) {
        ApiHost host = queryService.findHostById(hostId);

        boolean refresh = false;

        if (o.getHost() != null) {
            refresh = true;
            host.setHost(o.getHost());
        }

        if (o.getActive() != null) {
            refresh = true;
            host.setActive(o.getActive());
        }

        Optional.ofNullable(o.getDescription())
                .ifPresent(host::setDescription);

        host = hostRepo.save(host);
        return host;
    }

}
