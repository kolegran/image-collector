package com.github.kolegran.deviantart;

import io.micronaut.core.annotation.Introspected;

@Introspected
class DATag {

    private final String tag;
    private final Integer limit;
    private final Integer offset; // TODO: A1

    DATag(String tag, Integer limit, Integer offset) {
        this.tag = tag;
        this.limit = limit;
        this.offset = offset;
    }

    public String getTag() {
        return tag;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }
}
