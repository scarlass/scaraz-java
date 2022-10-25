package dev.scaraz.common.domain.audit;

import dev.scaraz.common.domain.BaseCriteria;
import org.springframework.data.jpa.domain.Specification;

public abstract class TimestampCriteria<E extends TimestampEntity> extends BaseCriteria<E> {
}
