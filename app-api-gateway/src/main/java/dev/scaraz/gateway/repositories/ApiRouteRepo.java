package dev.scaraz.gateway.repositories;

import dev.scaraz.gateway.entities.ApiRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRouteRepo extends JpaRepository<ApiRoute, Long> {
}
