package dev.scaraz.common.domain;

import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;

public abstract class BaseCriteria<E> implements Serializable {

    protected abstract Specification<E> toSpecification();

}
