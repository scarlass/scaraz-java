package dev.scaraz.core.datasource.repositories;

import dev.scaraz.core.datasource.domains.ApiEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiEntryRepo extends JpaRepository<ApiEntry, String> {

    boolean existsByPrefixContainsIgnoreCase(String prefix);

}
