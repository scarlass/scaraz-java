package dev.scaraz.gateway.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("springSecurityAuditorAware")
@RequiredArgsConstructor
public class DatasourceAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("system");
    }

}
