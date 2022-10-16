package dev.scaraz.common.utils.filters;

import java.util.List;

public class StringFilter extends Filter<String> {

    @Override
    public StringFilter setEq(String eq) {
        return (StringFilter) super.setEq(eq);
    }

    @Override
    public StringFilter setNotEq(String notEq) {
        return (StringFilter) super.setNotEq(notEq);
    }

    @Override
    public StringFilter setIn(List<String> in) {
        return (StringFilter) super.setIn(in);
    }

    @Override
    public StringFilter setContains(String contains) {
        return (StringFilter) super.setContains(contains);
    }

    @Override
    public StringFilter setSpecified(Boolean specified) {
        return (StringFilter) super.setSpecified(specified);
    }
}
