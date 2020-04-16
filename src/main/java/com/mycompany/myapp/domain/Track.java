package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Track.
 */
@Entity
@Table(name = "track")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "url")
    private String url;

    @Column(name = "popularity")
    private String popularity;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "released")
    private ZonedDateTime released;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "color")
    private String color;

    @ManyToMany(mappedBy = "tracks")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Playlist> playlists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Track name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public Track rating(Double rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public Track url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPopularity() {
        return popularity;
    }

    public Track popularity(String popularity) {
        this.popularity = popularity;
        return this;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Track thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Track createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getReleased() {
        return released;
    }

    public Track released(ZonedDateTime released) {
        this.released = released;
        return this;
    }

    public void setReleased(ZonedDateTime released) {
        this.released = released;
    }

    public Integer getDuration() {
        return duration;
    }

    public Track duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getColor() {
        return color;
    }

    public Track color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public Track playlists(Set<Playlist> playlists) {
        this.playlists = playlists;
        return this;
    }

    public Track addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
        playlist.getTracks().add(this);
        return this;
    }

    public Track removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
        playlist.getTracks().remove(this);
        return this;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Track)) {
            return false;
        }
        return id != null && id.equals(((Track) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Track{" +
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
