package dev.scaraz.gateway.web.rest;

import dev.scaraz.common.dto.request.*;
import dev.scaraz.common.dto.response.api.ApiEntryDTO;
import dev.scaraz.common.utils.PaginationUtil;
import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiHost;
import dev.scaraz.gateway.entities.ApiRoute;
import dev.scaraz.gateway.mapper.ApiMapper;
import dev.scaraz.gateway.service.ApiQueryService;
import dev.scaraz.gateway.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/routes")
public class ApiResource {

    private final ApiMapper apiMapper;
    private final ApiService apiService;
    private final ApiQueryService apiQueryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<ApiEntryDTO> page = apiQueryService.findAll(pageable)
                .map(apiMapper::toDTO);
        return new ResponseEntity<>(
                page.getContent(),
                PaginationUtil.getPaginationHeader(page),
                HttpStatus.OK
        );
    }


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable("id") long entryId) {
        return new ResponseEntity<>(
                apiMapper.toDTO(apiQueryService.findEntryById(entryId)),
                HttpStatus.OK
        );
    }


    @GetMapping(path = "/refresh")
    public ResponseEntity<?> refresh() {
        apiService.refresh();
        return ResponseEntity.ok().build();
    }


    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid CreateApiEntryDTO dto) {
        ApiEntry entry = apiService.create(dto);
        return new ResponseEntity<>(
                apiMapper.toDTO(entry),
                HttpStatus.CREATED
        );
    }

    @PutMapping(
            path = "/{entryId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable long entryId, @RequestBody UpdateApiEntryDTO dto) {
        ApiEntry entry = apiService.update(entryId, dto);
        return new ResponseEntity<>(
                apiMapper.toDTO(entry),
                HttpStatus.OK
        );
    }


    @PostMapping(
            path = "/host/{entryId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHosts(
            @PathVariable("entryId") long entryId,
            @RequestBody @Valid List<CreateApiHostDTO> dtos
    ) {
        ApiEntry entry = apiService.addHosts(entryId, dtos);
        return new ResponseEntity<>(
                apiMapper.toDTO(entry),
                HttpStatus.CREATED
        );
    }

    @PutMapping(
            path = "/host/{hostId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHost(
            @PathVariable long hostId,
            @RequestBody @Valid UpdateApiHostDTO dto
    ) {
        ApiHost host = apiService.updateHost(hostId, dto);
        return new ResponseEntity<>(
                apiMapper.toDTO(host),
                HttpStatus.OK
        );
    }


    @PostMapping(
            path = "/route/{entryId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRoutes(
            @PathVariable("entryId") long entryId,
            @RequestBody @Valid List<CreateApiRouteDTO> dtos
    ) {
        ApiEntry entry = apiService.addRoutes(entryId, dtos);
        return new ResponseEntity<>(
                apiMapper.toDTO(entry),
                HttpStatus.CREATED
        );
    }

    @PutMapping(
            path = "/route/{routeId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRoute(
            @PathVariable("routeId") long routeId,
            @RequestBody UpdateApiRouteDTO dto
    ) {
        ApiRoute route = apiService.updateRoute(routeId, dto);
        return new ResponseEntity<>(
                apiMapper.toDTO(route),
                HttpStatus.OK
        );
    }

}
