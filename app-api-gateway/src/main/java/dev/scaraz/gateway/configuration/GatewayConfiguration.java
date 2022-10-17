package dev.scaraz.gateway.configuration;

import dev.scaraz.gateway.configuration.properties.GatewayProperties;
import dev.scaraz.gateway.service.ApiQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {

    private final GatewayProperties gatewayProperties;

    @Bean
    public ApiRouteLocator routeLocator(ApiQueryService apiQueryService, RouteLocatorBuilder builder) {
        return new ApiRouteLocator(gatewayProperties, apiQueryService, builder);
    }

}
