package com.github.kolegran.deviantart.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

@JsonIgnoreProperties(ignoreUnknown = true)
@Introspected
public class ImageParameters {

    @JsonProperty("src")
    private String source;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("transparency")
    private Boolean transparency;

    @JsonProperty("filesize")
    private Integer filesize;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Boolean getTransparency() {
        return transparency;
    }

    public void setTransparency(Boolean transparency) {
        this.transparency = transparency;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    @Override
    public String toString() {
        return "ImageParameters{" +
            "source='" + source + '\'' +
            ", height=" + height +
            ", width=" + width +
            ", transparency=" + transparency +
            ", filesize=" + filesize +
            '}';
    }
}
