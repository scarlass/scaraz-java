package dev.scaraz.common.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "scaraz.app")
public class AppProperties {

    private Asynchronous async = new Asynchronous();

    @Getter
    @Setter
    public static class Asynchronous {
        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;
    }
}
