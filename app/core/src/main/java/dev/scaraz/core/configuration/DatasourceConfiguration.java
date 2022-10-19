package dev.scaraz.core.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "datasourceAuditorAware", dateTimeProviderRef = "utcDateTimeProvider")
public class DatasourceConfiguration {

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory factory) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(factory);
        return manager;
    }

    @Bean
    public DateTimeProvider utcDateTimeProvider() {
        return () -> Optional.of(Instant.now().atZone(ZoneId.of("Asia/Jakarta")));
    }

}
