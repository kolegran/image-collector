package com.github.kolegran.deviantart.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

@JsonIgnoreProperties(ignoreUnknown = true)
@Introspected
public class ImageStats {

    @JsonProperty("comments")
    private Integer comments;

    @JsonProperty("favourites")
    private Integer favourites;

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getFavourites() {
        return favourites;
    }

    public void setFavourites(Integer favourites) {
        this.favourites = favourites;
    }

    @Override
    public String toString() {
        return "ImageStats{" +
            "comments=" + comments +
            ", favourites=" + favourites +
            '}';
    }
}
