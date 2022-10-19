package dev.scaraz.core.web.resource.internal;

import dev.scaraz.core.mapper.ApiMapper;
import dev.scaraz.core.web.query.ApiQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/routes")
public class InternalApiResource {

    private final ApiMapper apiMapper;
    private final ApiQueryService apiQueryService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(
                apiQueryService.findAll().stream()
                        .map(apiMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

}
