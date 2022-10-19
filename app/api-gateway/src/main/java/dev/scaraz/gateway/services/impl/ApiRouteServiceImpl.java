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
import reactor.core.publisher.Flux;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiRouteServiceImpl implements ApiRouteService {

    private final ApiGatewayProperties apiGatewayProperties;

    private final CoreClient coreClient;
    private final KeycloakClient keycloakClient;

    private String token;

    private String getToken(boolean retry) {
        if (token == null || retry) {
            Response<ObjectNode> response = keycloakClient.accessToken(
                    "client_credentials",
                    apiGatewayProperties.getKcClientId(),
                    apiGatewayProperties.getKcClientSecret());

            if (response.isSuccessful()) {
                ObjectNode json = response.body();
                String access_token = json.get("access_token").asText();
                String token_type = json.get("token_type").asText();
                this.token = token_type + " " + access_token;
            }
        }
        return token;
    }

    @Override
    public List<ApiEntryDTO> getAllEntries(int retryAttempt) {
        Response<List<ApiEntryDTO>> response = coreClient.getAllEntries(getToken(false));
        if (!response.isSuccessful()) {
            if (response.code() == 401 && retryAttempt > 0) {
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {}
                getToken(true);
                return getAllEntries(retryAttempt - 1);
            }

            if (retryAttempt > 0)
                return getAllEntries(retryAttempt - 1);

            return new ArrayList<>();
        }

        return response.body();
    }

}
