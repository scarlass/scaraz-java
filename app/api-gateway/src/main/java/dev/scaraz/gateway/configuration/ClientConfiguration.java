package dev.scaraz.gateway.configuration;

import dev.scaraz.common.configuration.properties.AppProperties;
import dev.scaraz.gateway.clients.CoreClient;
import dev.scaraz.gateway.clients.KeycloakClient;
import dev.scaraz.gateway.configuration.properties.ApiGatewayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

    private final OAuth2ResourceServerProperties resourceServerProperties;

    private final ApiGatewayProperties gatewayProperties;

    @PostConstruct
    private void init() {
        log.debug("Issuer URI: {}", resourceServerProperties.getJwt().getIssuerUri());
    }

    @Bean
    public CoreClient coreClient(Retrofit.Builder retrofitBuilder) {
        String url = gatewayProperties.getCoreSvcUrl();
        if (!url.endsWith("/")) url += "/";

        return retrofitBuilder.baseUrl(url)
                .build()
                .create(CoreClient.class);
    }

    @Bean
    public KeycloakClient keycloakClient(Retrofit.Builder retrofitBuilder) {
        String url = resourceServerProperties.getJwt().getIssuerUri();
        if (!url.endsWith("/")) url += "/";
        return retrofitBuilder
                .baseUrl(url)
                .build()
                .create(KeycloakClient.class);
    }

}
