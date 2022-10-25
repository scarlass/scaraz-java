package dev.scaraz.common.configuration.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Configuration;

import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "scaraz.app")
public class AppProperties {

    private LogLevel logLevel = LogLevel.INFO;

    private String logDirectory = System.getenv("LOG_DIR");

    private Asynchronous async = new Asynchronous();

    private BaseUrls baseUrls = new BaseUrls();

    private Retrofit retrofit = new Retrofit();

    @Getter
    @Setter
    public static class Asynchronous {
        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;
    }

    public static class BaseUrls extends TreeMap<String, String> {
    }

    @Getter
    @Setter
    public static class Retrofit {

        private HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.NONE;

        private Duration writeTimeout = Duration.ofMinutes(5);

        private Duration readTimeout = Duration.ofMinutes(10);

        private Duration callTimeout = Duration.ofMinutes(5);

    }

}
