package dev.scaraz.gateway.configuration;

import dev.scaraz.gateway.configuration.properties.ApiGatewayProperties;
import dev.scaraz.gateway.configuration.utils.KeycloakGrantedAuthoritiesConverter;
import dev.scaraz.gateway.configuration.utils.KeycloakJwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.zalando.problem.spring.webflux.advice.security.SecurityProblemSupport;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration {

    private final ApiGatewayProperties apiGatewayProperties;

    private final SecurityProblemSupport problemSupport;

    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> authenticationConverter() {
        return new KeycloakJwtAuthenticationConverter(apiGatewayProperties.getKcClientId());
    }

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(problemSupport)
                        .accessDeniedHandler(problemSupport))

                .authorizeExchange(auth -> auth
                        .pathMatchers("/actuator/**").authenticated()
                        .anyExchange().permitAll())

                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
                .build();
    }

}
