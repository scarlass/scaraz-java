package dev.scaraz.gateway;

import dev.scaraz.common.configuration.AppProperties;
import dev.scaraz.common.configuration.ScarazInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties({ AppProperties.class })
public class ApiGatewayApplication implements CommandLineRunner {

    private final Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        SpringApplication app = new SpringApplication();
        ScarazInitializer.setDefaultProfile(app);

        Environment env = app.run(args).getEnvironment();
    }

    @PostConstruct
    private void init() {
        ScarazInitializer.checkActiveProfile(environment);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
