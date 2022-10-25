package dev.scaraz.common.utils.filters;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Filter<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private T eq;
    private T notEq;
    private List<T> in;
    private T contains;
    private Boolean specified;

    public Filter<T> setEq(T eq) {
        this.eq = eq;
        return this;
    }

    public Filter<T> setNotEq(T notEq) {
        this.notEq = notEq;
        return this;
    }

    public Filter<T> setIn(List<T> in) {
        this.in = in;
        return this;
    }

    public Filter<T> setContains(T contains) {
        this.contains = contains;
        return this;
    }

    public Filter<T> setSpecified(Boolean specified) {
        this.specified = specified;
        return this;
    }
}
