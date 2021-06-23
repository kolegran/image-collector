package com.github.kolegran.deviantart;

import io.micronaut.core.annotation.Introspected;

@Introspected
class DATagInfo {

    private String tag;
    private Integer limit;
    private Integer offset;

    DATagInfo(String tag, Integer limit, Integer offset) {
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

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
