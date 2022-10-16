package dev.scaraz.gateway;

import dev.scaraz.common.configuration.properties.AppProperties;
import dev.scaraz.gateway.configuration.properties.GatewayProperties;
import dev.scaraz.common.configuration.ScarazInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan({"dev.scaraz.gateway", "dev.scaraz.common.configuration"})
@EnableConfigurationProperties({AppProperties.class, GatewayProperties.class})
public class ApiGatewayApplication implements CommandLineRunner {

    private final Environment environment;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApiGatewayApplication.class);
        ScarazInitializer.setDefaultProfile(app);

        Environment env = app.run(args).getEnvironment();
        String port = env.getProperty("server.port");
        log.info("Application Running on port {}", port);
    }

    @PostConstruct
    private void init() {
        ScarazInitializer.checkActiveProfile(environment);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
