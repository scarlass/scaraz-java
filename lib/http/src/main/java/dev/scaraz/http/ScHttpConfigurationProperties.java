package dev.scaraz.http;

import lombok.Getter;
import lombok.Setter;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "scaraz.http")
public class ScHttpConfigurationProperties {

    private boolean enabled = false;

    private HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.NONE;

    private Duration writeTimeout = Duration.ofMinutes(5);

    private Duration readTimeout = Duration.ofMinutes(10);

    private Duration callTimeout = Duration.ofMinutes(5);

}
