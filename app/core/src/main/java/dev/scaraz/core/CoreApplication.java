package dev.scaraz.core;

import dev.scaraz.common.configuration.ScarazInitializer;
import dev.scaraz.common.configuration.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
@ComponentScan({"dev.scaraz.core", "dev.scaraz.common.configuration"})
@EnableConfigurationProperties({AppProperties.class})
public class CoreApplication {

    private final Environment environment;

    @PostConstruct
    private void init() {
        ScarazInitializer.checkActiveProfile(environment);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CoreApplication.class);

        ConfigurableApplicationContext ctx = app.run(args);
        log.info("Application running on port {}", ctx.getEnvironment().getProperty("server.port"));
    }

}
