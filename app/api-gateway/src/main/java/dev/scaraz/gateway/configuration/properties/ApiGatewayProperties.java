package dev.scaraz.gateway.configuration.properties;

import dev.scaraz.common.configuration.properties.AppProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "scaraz.gateway")
public class ApiGatewayProperties {

    private String coreSvcUrl;
    private String kcClientId;
    private String kcClientSecret;
    private Initial initial = new Initial();

    @Getter
    @Setter
    @ToString
    public static class Initial {

        private int retryAttempt = 10;
        private int retryTimeout = 5;

    }

}
