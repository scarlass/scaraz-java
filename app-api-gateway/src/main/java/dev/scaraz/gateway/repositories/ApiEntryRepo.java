package dev.scaraz.gateway.repositories;

import dev.scaraz.gateway.entity.ApiEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiEntryRepo extends JpaRepository<ApiEntry, Long> {
}
