package dev.scaraz.gateway.configuration;

import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiRoute;
import dev.scaraz.gateway.repositories.ApiEntryRepo;
import dev.scaraz.gateway.service.ApiQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
public class ApiRouteLocator implements RouteLocator {

    private final ApiQueryService apiQueryService;

    private final RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        List<ApiEntry> entries = apiQueryService.findAll();

        for (ApiEntry entry : entries) {
            for (ApiRoute route : entry.getRoutes()) {
                routesBuilder.route(
                        String.valueOf(route.getId()),
                        predicateSpec -> attachRoute(entry, route, predicateSpec));
            }
        }

        return routesBuilder.build().getRoutes();
    }

    private Buildable<Route> attachRoute(ApiEntry entry, ApiRoute route, PredicateSpec spec) {
        String uri = entry.getPrefix() + route.getPath();

        if (route.getMethod() != null)
            spec.method(route.getMethod());

        if (route.getVariables() != null)
            spec.path(false, route.getVariables());

        return spec.uri(uri);
    }

}
