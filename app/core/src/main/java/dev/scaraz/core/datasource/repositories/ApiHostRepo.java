package dev.scaraz.core.datasource.repositories;

import dev.scaraz.core.datasource.domains.ApiHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiHostRepo extends JpaRepository<ApiHost, String> {
}
