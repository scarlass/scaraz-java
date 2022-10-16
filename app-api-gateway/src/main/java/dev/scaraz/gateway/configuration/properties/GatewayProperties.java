package dev.scaraz.gateway.configuration.properties;

import dev.scaraz.common.utils.enums.HostProfile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "scaraz.gateway")
public class GatewayProperties {

    private HostProfile hostProfile;

}
