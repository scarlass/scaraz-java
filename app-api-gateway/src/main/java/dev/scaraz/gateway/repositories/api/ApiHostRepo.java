package dev.scaraz.gateway.repositories.api;

import dev.scaraz.gateway.entities.ApiHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiHostRepo extends JpaRepository<ApiHost, Long> {
}
