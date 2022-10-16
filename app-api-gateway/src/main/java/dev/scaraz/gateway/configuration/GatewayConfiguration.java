package dev.scaraz.gateway.configuration;

import dev.scaraz.gateway.configuration.properties.GatewayProperties;
import dev.scaraz.gateway.service.ApiQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class GatewayConfiguration {

    private final GatewayProperties gatewayProperties;

    @Bean
    public ApiRouteLocator routeLocator(ApiQueryService apiQueryService, RouteLocatorBuilder builder) {
        return new ApiRouteLocator(gatewayProperties, apiQueryService, builder);
    }

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange(r -> r.anyExchange().permitAll())
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .logout().disable()
                .build();
    }

}
