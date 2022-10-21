package dev.scaraz.core.web.resource;

import dev.scaraz.common.dto.request.CreateApiEntryDTO;
import dev.scaraz.common.dto.request.CreateApiHostDTO;
import dev.scaraz.common.dto.request.UpdateApiEntryDTO;
import dev.scaraz.common.dto.request.UpdateApiHostDTO;
import dev.scaraz.core.datasource.domains.ApiEntry;
import dev.scaraz.core.datasource.domains.ApiHost;
import dev.scaraz.core.mapper.ApiMapper;
import dev.scaraz.core.service.ApiService;
import dev.scaraz.core.web.query.ApiQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/routes")
public class ApiResource {

    private final ApiMapper apiMapper;
    private final ApiService apiService;
    private final ApiQueryService apiQueryService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody CreateApiEntryDTO o) {
        ApiEntry entry = apiService.create(o);
        return new ResponseEntity<>(
                apiMapper.toDTO(entry),
                HttpStatus.CREATED);
    }

    @PutMapping(path = "/{entryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @PathVariable String entryId,
            @Valid @RequestBody UpdateApiEntryDTO o
    ) {
        ApiEntry entry = apiService.update(entryId, o);
        return new ResponseEntity<>(
                apiMapper.toDTO(entry),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/host/{entryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHost(
            @PathVariable String entryId,
            @Valid @RequestBody List<CreateApiHostDTO> o
    ) {
        ApiEntry entry = apiService.createHosts(entryId, o);
        return new ResponseEntity<>(
                apiMapper.toDTO(entry),
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "/host/{entryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHost(
            @PathVariable String entryId,
            @Valid @RequestBody UpdateApiHostDTO o
    ) {
        ApiHost apiHost = apiService.updateHost(entryId, o);
        return  ResponseEntity.ok(apiMapper.toDTO(apiHost));
    }

}
