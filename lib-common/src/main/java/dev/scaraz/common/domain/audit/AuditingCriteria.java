package dev.scaraz.common.domain.audit;

import dev.scaraz.common.domain.audit.AuditingEntity;
import dev.scaraz.common.domain.BaseCriteria;
import org.springframework.data.jpa.domain.Specification;

public abstract class AuditingCriteria<E extends AuditingEntity> extends BaseCriteria<E> {

    public Specification<E> auditSpecification(Specification<E> spec) {

        return spec;
    }
}
