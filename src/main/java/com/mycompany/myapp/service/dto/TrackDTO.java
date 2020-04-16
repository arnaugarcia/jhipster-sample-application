package com.mycompany.myapp.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Track} entity.
 */
public class TrackDTO implements Serializable {
    
    private Long id;

    private String name;

    private Double rating;

    private String url;

    private String popularity;

    private String thumbnail;

    private ZonedDateTime createdAt;

    private ZonedDateTime released;

    private Integer duration;

    private String color;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getReleased() {
        return released;
    }

    public void setReleased(ZonedDateTime released) {
        this.released = released;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrackDTO trackDTO = (TrackDTO) o;
        if (trackDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trackDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrackDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rating=" + getRating() +
            ", url='" + getUrl() + "'" +
            ", popularity='" + getPopularity() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", released='" + getReleased() + "'" +
            ", duration=" + getDuration() +
            ", color='" + getColor() + "'" +
            "}";
    }
}
