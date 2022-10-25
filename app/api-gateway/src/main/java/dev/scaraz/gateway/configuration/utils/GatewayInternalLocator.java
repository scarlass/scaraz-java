package dev.scaraz.gateway.configuration.utils;

import dev.scaraz.common.dto.response.api.ApiEntryDTO;
import dev.scaraz.common.dto.response.api.ApiHostDTO;
import dev.scaraz.gateway.services.ApiRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class GatewayInternalLocator implements RouteLocator {

    private final ApiRouteService routeService;
    private final RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        log.info("Get accessible routes...");

        List<ApiEntryDTO> entries = routeService.getAllEntries();
        for (ApiEntryDTO entry : entries) {
            for (ApiHostDTO host : entry.getHosts()) {
                if (!host.isActive()) continue;
                routes.route(
                        host.getId(),
                        predicateSpec -> attachRoute(entry, host, predicateSpec));
            }
        }

        return routes.build().getRoutes();
    }

    private Buildable<Route> attachRoute(ApiEntryDTO entry, ApiHostDTO host, PredicateSpec spec) {
        spec.path(true, entry.getPrefix() + "/**")
                .filters(f -> f
                        .rewritePath(entry.getPrefix() + "/(?<segment>.*)", "/${segment}"));

        return spec.uri(host.getHost());
    }

}
