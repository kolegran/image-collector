package com.github.kolegran.deviantart.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

import java.util.List;

@Introspected
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageInfo {

    @JsonProperty("next_offset")
    private Integer nextOffset;

    @JsonProperty("results")
    private List<ImageDetails> imageDetails;

    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public List<ImageDetails> getImageDetails() {
        return imageDetails;
    }

    public void setImageDetails(List<ImageDetails> imageDetails) {
        this.imageDetails = imageDetails;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
            "nextOffset=" + nextOffset +
            ", imageDetails=" + imageDetails +
            '}';
    }
}
