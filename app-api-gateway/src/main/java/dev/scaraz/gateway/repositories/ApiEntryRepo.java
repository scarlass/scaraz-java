package dev.scaraz.gateway.repositories;

import dev.scaraz.gateway.entities.ApiEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiEntryRepo extends JpaRepository<ApiEntry, Long> {

    boolean existsByPrefixContainsIgnoreCase(String prefix);

}
