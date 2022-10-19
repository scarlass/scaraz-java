package dev.scaraz.core.web.query;

import dev.scaraz.core.datasource.domains.ApiEntry;
import dev.scaraz.core.datasource.domains.ApiHost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApiQueryService {
    ApiEntry findById(String entryId);

    List<ApiEntry> findAll();

    Page<ApiEntry> findAll(Pageable pageable);

    ApiHost findHostById(String entryId);

    List<ApiHost> findAllHost();

    Page<ApiHost> findAllHost(Pageable pageable);
}
