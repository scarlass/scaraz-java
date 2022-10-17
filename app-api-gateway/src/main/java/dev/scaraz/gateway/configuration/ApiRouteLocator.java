package dev.scaraz.gateway.configuration;

import dev.scaraz.common.utils.enums.HostProfile;
import dev.scaraz.gateway.configuration.properties.GatewayProperties;
import dev.scaraz.gateway.entities.ApiEntry;
import dev.scaraz.gateway.entities.ApiHost;
import dev.scaraz.gateway.entities.ApiRoute;
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

    private final GatewayProperties gatewayProperties;

    private final ApiQueryService apiQueryService;

    private final RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        List<ApiEntry> entries = apiQueryService.findAll();

        final boolean isAllProfile = gatewayProperties
                .getHostProfile()
                .equals(HostProfile.ALL);

        for (ApiEntry entry : entries) {
            for (ApiHost host : entry.getHosts()) {
                HostProfile profile = host.getProfile();

                boolean shouldAttachRoute = isAllProfile || profile.equals(gatewayProperties.getHostProfile());

                if (!shouldAttachRoute) continue;
                else if (!host.isActive()) continue;

                for (ApiRoute route : entry.getRoutes()) {
                    if (!route.isActive()) continue;
                    routesBuilder.route(
                            String.valueOf(route.getId()),
                            predicateSpec -> attachRoute(entry, host, route, predicateSpec));
                }
            }
        }

        return routesBuilder.build().getRoutes();
    }

    private Buildable<Route> attachRoute(ApiEntry entry, ApiHost host, ApiRoute route, PredicateSpec spec) {
        if (route.getMethod() != null)
            spec.method(route.getMethod());

        if (route.getVariables() != null) {
            spec.path(true, entry.getPrefix() + route.getPath());
        }

        return spec.uri(host.getHost());
    }

}
