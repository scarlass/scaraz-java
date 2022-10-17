package dev.scaraz.gateway.configuration;

import dev.scaraz.gateway.configuration.security.ReactiveKeycloakJwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.zalando.problem.spring.webflux.advice.security.SecurityProblemSupport;

@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
public class WebSecurityConfiguration {

    @Autowired
    private SecurityProblemSupport securityProblemSupport;

    private final ReactiveKeycloakJwtAuthenticationConverter jwtAuthenticationConverter;

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling(h -> h
                        .authenticationEntryPoint(securityProblemSupport)
                        .accessDeniedHandler(securityProblemSupport)
                )
                .authorizeExchange(r -> r
                        .pathMatchers("/actuator/**", "/internal/**").authenticated()
                        .anyExchange().permitAll()
                        .and()
                        .oauth2ResourceServer().jwt(spec ->  spec
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .build();
    }

}
