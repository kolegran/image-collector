package com.github.kolegran.deviantart;

import io.micronaut.core.annotation.Introspected;

import java.util.List;

@Introspected
class DARequestParameters {

    private final List<DATag> tags;

    DARequestParameters(List<DATag> tags) {
        this.tags = tags;
    }

    public List<DATag> getTags() {
        return tags;
    }
}
