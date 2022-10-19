package dev.scaraz.gateway.configuration;

import dev.scaraz.common.configuration.properties.AppProperties;
import dev.scaraz.gateway.clients.CoreClient;
import dev.scaraz.gateway.clients.KeycloakClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

    private final AppProperties appProperties;
    private final OAuth2ResourceServerProperties resourceServerProperties;

    private final RetrofitConfiguration retrofitConfiguration;

    @Bean
    public CoreClient coreClient() {
        return retrofitConfiguration.create(
                appProperties.getBaseUrls().get("core-service"),
                CoreClient.class
        );
    }

    @Bean
    public KeycloakClient keycloakClient() {
        return retrofitConfiguration.create(
                resourceServerProperties.getJwt().getIssuerUri(),
                KeycloakClient.class
        );
    }

}
