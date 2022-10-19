package dev.scaraz.gateway.configuration.utils;

import dev.scaraz.common.dto.response.api.ApiEntryDTO;
import dev.scaraz.common.dto.response.api.ApiHostDTO;
import dev.scaraz.gateway.services.ApiRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GatewayLocator implements RouteLocator {

    private final ApiRouteService routeService;
    private final RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        for (ApiEntryDTO entry : routeService.getAllEntries(10)) {
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
