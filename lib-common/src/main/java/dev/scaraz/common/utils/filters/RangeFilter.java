package dev.scaraz.common.utils.filters;

import lombok.Getter;

import java.util.List;

public class RangeFilter<C extends Comparable<? super C>> extends Filter<C> {

    private C gt;

    private C gte;

    private C lt;

    private C lte;

    public RangeFilter<C> setGreaterThan(C gt) {
        this.gt = gt;
        return this;
    }
    public C getGreaterThan() {
        return gt;
    }

    public RangeFilter<C> setGreaterThanEq(C gte) {
        this.gte = gte;
        return this;
    }
    public C getGreaterThanEq() {
        return gte;
    }

    public RangeFilter<C> setLessThan(C lt) {
        this.lt = lt;
        return this;
    }
    public C getLessThan() {
        return lt;
    }

    public RangeFilter<C> setLessThanEq(C lte) {
        this.lte = lte;
        return this;
    }
    public C getLessThanEqual() {
        return lte;
    }

    @Override
    public RangeFilter<C> setEq(C eq) {
        return (RangeFilter<C>) super.setEq(eq);
    }

    @Override
    public RangeFilter<C> setNotEq(C notEq) {
        return (RangeFilter<C>) super.setNotEq(notEq);
    }

    @Override
    public RangeFilter<C> setIn(List<C> in) {
        return (RangeFilter<C>) super.setIn(in);
    }

    @Override
    public RangeFilter<C> setContains(C contains) {
        return (RangeFilter<C>) super.setContains(contains);
    }

    @Override
    public RangeFilter<C> setSpecified(Boolean specified) {
        return (RangeFilter<C>) super.setSpecified(specified);
    }
}
