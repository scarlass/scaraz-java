package dev.scaraz.common.configuration.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
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
        public static final String LITERAL_PATTERN = "^(\\d+)(m|h|s|d|ms)$";

        private HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.NONE;

        @Getter(AccessLevel.NONE)
        private String writeTimeout = "5m";

        @Getter(AccessLevel.NONE)
        private String readTimeout = "10m";

        @Getter(AccessLevel.NONE)
        private String callTimeout = "5m";

        public Duration getWriteTimeout() {
            return parseFromString(writeTimeout);
        }

        public Duration getReadTimeout() {
            return parseFromString(readTimeout);
        }

        public Duration getCallTimeout() {
            return parseFromString(callTimeout);
        }

        private Duration parseFromString(String literal) {
            Pattern pattern = Pattern.compile(LITERAL_PATTERN, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(literal);
            if (!matcher.matches())
                throw new InvalidParameterException("cannot parse timeout literal from input \"" + literal + "\"");

            long time = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2).toUpperCase();
            switch (unit) {
                case "MS":
                    return Duration.ofMillis(time);
                case "S":
                    return Duration.ofSeconds(time);
                case "M":
                    return Duration.ofMinutes(time);
                case "H":
                    return Duration.ofHours(time);
                case "D":
                    return Duration.ofDays(time);
            }

            return Duration.ZERO;
        }
    }

}
