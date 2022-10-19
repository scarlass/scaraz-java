package dev.scaraz.gateway.configuration;

import dev.scaraz.gateway.configuration.utils.GatewayLocator;
import dev.scaraz.gateway.services.ApiRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class GatewayConfiguration {

    private final ApiRouteService routeService;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return new GatewayLocator(routeService, routeLocatorBuilder);
    }

}
