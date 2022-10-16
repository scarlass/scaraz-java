package dev.scaraz.gateway.service.impl;

import dev.scaraz.common.configuration.MessageTranslator;
import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiHost;
import dev.scaraz.gateway.entities.ApiRoute;
import dev.scaraz.gateway.repositories.ApiEntryRepo;
import dev.scaraz.gateway.repositories.ApiRouteRepo;
import dev.scaraz.gateway.repositories.api.ApiHostRepo;
import dev.scaraz.gateway.service.ApiQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
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
    private final ApiRouteRepo routeRepo;

    private final MessageTranslator translator;

    @Override
    public ApiEntry findEntryById(long id) {
        return entryRepo.findById(id)
                .orElseThrow(this::throwNotFound);
    }

    @Override
    public ApiHost findHostById(long id) {
        return hostRepo.findById(id)
                .orElseThrow(this::throwNotFound);
    }

    @Override
    public ApiRoute findRouteById(long id) {
        return routeRepo.findById(id)
                .orElseThrow(this::throwNotFound);
    }

    @Override
    public List<ApiEntry> findAll() {
        return entryRepo.findAll();
    }

    @Override
    public Page<ApiEntry> findAll(Pageable pageable) {
        return entryRepo.findAll(pageable);
    }

    private NotFoundException throwNotFound() {
        return NotFoundException.create(
                true,
                translator.message("err.not.found"));
    }

}
