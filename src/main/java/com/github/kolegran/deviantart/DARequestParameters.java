package com.github.kolegran.deviantart;

import io.micronaut.core.annotation.Introspected;

import java.util.List;

@Introspected
class DARequestParameters {

    private final List<DATagInfo> tags;

    DARequestParameters(List<DATagInfo> tags) {
        this.tags = tags;
    }

    public List<DATagInfo> getTags() {
        return tags;
    }
}
