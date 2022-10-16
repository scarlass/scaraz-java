package dev.scaraz.gateway.service;

import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApiQueryService {
    ApiEntry findEntryById(long id);

    ApiRoute findRouteById(long id);

    List<ApiEntry> findAll();

    Page<ApiEntry> findAll(Pageable pageable);
}
