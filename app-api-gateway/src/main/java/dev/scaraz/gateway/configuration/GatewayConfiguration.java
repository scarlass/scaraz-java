package dev.scaraz.gateway.configuration;

import dev.scaraz.gateway.repositories.ApiEntryRepo;
import dev.scaraz.gateway.service.ApiQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {

    @Bean("routeLocator")
    public ApiRouteLocator routeLocator(ApiQueryService apiQueryService, RouteLocatorBuilder builder) {
        return new ApiRouteLocator(apiQueryService, builder);
    }

}
