package dev.scaraz.gateway.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfiguration {

    private final OAuth2ResourceServerProperties resourceServerProperties;

    @Bean
    public ReactiveKeycloakJwtAuthenticationConverter keycloakJwtAuthenticationConverter() {
        return new ReactiveKeycloakJwtAuthenticationConverter(
                new KeycloakGrantedAuthoritiesConverter(resourceServerProperties.getOpaquetoken().getClientId()));
    }

}
