package dev.scaraz.core.web.query.impl;

import dev.scaraz.core.datasource.domains.ApiEntry;
import dev.scaraz.core.datasource.domains.ApiHost;
import dev.scaraz.core.datasource.repositories.ApiEntryRepo;
import dev.scaraz.core.datasource.repositories.ApiHostRepo;
import dev.scaraz.core.web.query.ApiQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiQueryServiceImpl implements ApiQueryService {

    private final ApiEntryRepo entryRepo;
    private final ApiHostRepo hostRepo;

    @Override
    public ApiEntry findById(String entryId) {
        return entryRepo.findById(entryId)
                .orElseThrow();
    }

    @Override
    public List<ApiEntry> findAll() {
        return entryRepo.findAll();
    }

    @Override
    public Page<ApiEntry> findAll(Pageable pageable) {
        return entryRepo.findAll(pageable);
    }


    @Override
    public ApiHost findHostById(String hostId) {
        return hostRepo.findById(hostId)
                .orElseThrow();
    }

    @Override
    public List<ApiHost> findAllHost() {
        return hostRepo.findAll();
    }

    @Override
    public Page<ApiHost> findAllHost(Pageable pageable) {
        return hostRepo.findAll(pageable);
    }

}
