package dev.scaraz.gateway;

import dev.scaraz.common.configuration.ScarazInitializer;
import dev.scaraz.common.configuration.properties.AppProperties;
import dev.scaraz.gateway.configuration.properties.ApiGatewayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.annotation.PostConstruct;

@EnableWebFlux
@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties({AppProperties.class, ApiGatewayProperties.class})
public class ApiGatewayApplication {

    private final Environment environment;

    @PostConstruct
    private void init() {
        ScarazInitializer.checkActiveProfile(environment);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApiGatewayApplication.class);
        ScarazInitializer.setDefaultProfile(app);
        app.run(args);
    }

}
