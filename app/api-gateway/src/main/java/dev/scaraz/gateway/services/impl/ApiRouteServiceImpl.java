package dev.scaraz.gateway.services.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.scaraz.common.dto.response.api.ApiEntryDTO;
import dev.scaraz.gateway.clients.CoreClient;
import dev.scaraz.gateway.clients.KeycloakClient;
import dev.scaraz.gateway.configuration.properties.ApiGatewayProperties;
import dev.scaraz.gateway.services.ApiRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiRouteServiceImpl implements ApiRouteService {

    private final ApiGatewayProperties apiGatewayProperties;

    private final CoreClient coreClient;
    private final KeycloakClient keycloakClient;

    private String token;

    private final AtomicInteger retryAttempt = new AtomicInteger();

    @PostConstruct
    private void init() {
        retryAttempt.set(apiGatewayProperties.getInitial().getRetryAttempt());
    }

    private void resetRetryCounter() {
        retryAttempt.set(apiGatewayProperties.getInitial().getRetryAttempt());
    }
    private boolean isRetryable() {
        return retryAttempt.get() > 0;
    }
    private void waitToRetry() {
        try {
            int retryTimeout = apiGatewayProperties.getInitial().getRetryTimeout();
            log.info("Retrying in {}s", retryTimeout);
            Thread.sleep(retryTimeout * 1000L);
        }
        catch (InterruptedException e) {
        }
    }

    private String getToken(boolean retry) {
        if (token == null || (retry && isRetryable())) {
            return keycloakClient.accessToken(
                            "client_credentials",
                            apiGatewayProperties.getKcClientId(),
                            apiGatewayProperties.getKcClientSecret()
                    ).map(response -> {
                        log.debug("Get Token success ? {}", response.isSuccessful());
                        if (response.isSuccessful()) {
                            ObjectNode json = response.body();
                            String access_token = json.get("access_token").asText();
                            String token_type = json.get("token_type").asText();
                            return this.token = token_type + " " + access_token;
                        }

                        waitToRetry();

                        retryAttempt.decrementAndGet();
                        return getToken(true);
                    })
                    .doOnCompleted(this::resetRetryCounter)
                    .toBlocking()
                    .single();
        }
        return token;
    }

    @Override
    public List<ApiEntryDTO> getAllEntries() {
        return coreClient.getAllEntries(getToken(false))
                .map(response -> {
                    if (response.isSuccessful()) return response.body();

                    if (response.code() == 401) {
                        log.debug("Unauthorized response when fetching routes...");
                        if (retryAttempt.get() > 0) {
                            getToken(true);
                            waitToRetry();
                            return getAllEntries();
                        }
                    }

                    if (retryAttempt.get() > 0) {
                        retryAttempt.decrementAndGet();
                        return getAllEntries();
                    }

                    return new ArrayList<ApiEntryDTO>();
                })
                .onErrorReturn(r -> {
                    log.warn("Couldnt get registered routes, reason: {}", r.getMessage());
                    return new ArrayList<>();
                })
                .doOnCompleted(this::resetRetryCounter)
                .toBlocking()
                .single();
    }

}
