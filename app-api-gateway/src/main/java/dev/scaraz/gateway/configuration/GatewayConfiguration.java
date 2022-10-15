package dev.scaraz.gateway.configuration;

import dev.scaraz.gateway.repositories.ApiEntryRepo;
import dev.scaraz.gateway.service.ApiRouteLocator;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {

    @Bean("routeLocator")
    public ApiRouteLocator routeLocator(ApiEntryRepo apiEntryRepo, RouteLocatorBuilder builder) {
        return new ApiRouteLocator(apiEntryRepo, builder);
    }

}
