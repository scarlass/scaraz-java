package dev.scaraz.core.configuration.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatasourceAuditorAware implements AuditorAware<String> {

    private final AppSecurity appSecurity;

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Optional.of(appSecurity.getCurrentUsername());
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
        return Optional.of("system");
    }

}
