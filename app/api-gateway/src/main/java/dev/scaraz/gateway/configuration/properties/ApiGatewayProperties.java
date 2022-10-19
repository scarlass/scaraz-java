package dev.scaraz.gateway.configuration.properties;

import dev.scaraz.common.configuration.properties.AppProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "scaraz.gateway")
public class ApiGatewayProperties {

    private String coreSvcUrl;
    private String kcClientId;
    private String kcClientSecret;

}
