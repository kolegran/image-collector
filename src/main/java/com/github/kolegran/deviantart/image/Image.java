package com.github.kolegran.deviantart.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

@JsonIgnoreProperties(ignoreUnknown = true)
@Introspected
public class Image {

    @JsonProperty("deviationid")
    private String deviationId;

    @JsonProperty("url")
    private String imageUrl;

    @JsonProperty("title")
    private String title;

    @JsonProperty("category")
    private String category;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    @JsonProperty("is_published")
    private Boolean isPublished;

    @JsonProperty("published_time")
    private Integer publishedTime;

    @JsonProperty("is_downloadable")
    private Boolean isDownloadable;

    @JsonProperty("allows_comments")
    private Boolean allowsComments;

    @JsonProperty("stats")
    private ImageStats imageStats;

    @JsonProperty("content")
    private ImageParameters imageParameters;

    public String getDeviationId() {
        return deviationId;
    }

    public void setDeviationId(String deviationId) {
        this.deviationId = deviationId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Integer getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Integer publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Boolean getDownloadable() {
        return isDownloadable;
    }

    public void setDownloadable(Boolean downloadable) {
        isDownloadable = downloadable;
    }

    public Boolean getAllowsComments() {
        return allowsComments;
    }

    public void setAllowsComments(Boolean allowsComments) {
        this.allowsComments = allowsComments;
    }

    public ImageStats getImageStats() {
        return imageStats;
    }

    public void setImageStats(ImageStats imageStats) {
        this.imageStats = imageStats;
    }

    public ImageParameters getImageParameters() {
        return imageParameters;
    }

    public void setImageParameters(ImageParameters imageParameters) {
        this.imageParameters = imageParameters;
    }

    @Override
    public String toString() {
        return "ImageDetails{" +
            "deviationId='" + deviationId + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", title='" + title + '\'' +
            ", category='" + category + '\'' +
            ", isDeleted=" + isDeleted +
            ", isPublished=" + isPublished +
            ", publishedTime=" + publishedTime +
            ", isDownloadable=" + isDownloadable +
            ", allowsComments=" + allowsComments +
            ", imageStats=" + imageStats +
            ", imageParameters=" + imageParameters +
            '}';
    }
}
