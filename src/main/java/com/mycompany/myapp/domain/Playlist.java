package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Playlist.
 */
@Entity
@Table(name = "playlist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "collaborative")
    private Boolean collaborative;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "primary_color")
    private String primaryColor;

    @Column(name = "cover")
    private String cover;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "public_accessible")
    private Boolean publicAccessible;

    @Column(name = "number_songs")
    private Integer numberSongs;

    @Column(name = "followers")
    private Integer followers;

    @Column(name = "rating")
    private Double rating;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "playlist_track",
               joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id"))
    private Set<Track> tracks = new HashSet<>();

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

    public Playlist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isCollaborative() {
        return collaborative;
    }

    public Playlist collaborative(Boolean collaborative) {
        this.collaborative = collaborative;
        return this;
    }

    public void setCollaborative(Boolean collaborative) {
        this.collaborative = collaborative;
    }

    public String getDescription() {
        return description;
    }

    public Playlist description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public Playlist primaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getCover() {
        return cover;
    }

    public Playlist cover(String cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Playlist thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Boolean isPublicAccessible() {
        return publicAccessible;
    }

    public Playlist publicAccessible(Boolean publicAccessible) {
        this.publicAccessible = publicAccessible;
        return this;
    }

    public void setPublicAccessible(Boolean publicAccessible) {
        this.publicAccessible = publicAccessible;
    }

    public Integer getNumberSongs() {
        return numberSongs;
    }

    public Playlist numberSongs(Integer numberSongs) {
        this.numberSongs = numberSongs;
        return this;
    }

    public void setNumberSongs(Integer numberSongs) {
        this.numberSongs = numberSongs;
    }

    public Integer getFollowers() {
        return followers;
    }

    public Playlist followers(Integer followers) {
        this.followers = followers;
        return this;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Double getRating() {
        return rating;
    }

    public Playlist rating(Double rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public Playlist tracks(Set<Track> tracks) {
        this.tracks = tracks;
        return this;
    }

    public Playlist addTrack(Track track) {
        this.tracks.add(track);
        track.getPlaylists().add(this);
        return this;
    }

    public Playlist removeTrack(Track track) {
        this.tracks.remove(track);
        track.getPlaylists().remove(this);
        return this;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Playlist)) {
            return false;
        }
        return id != null && id.equals(((Playlist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Playlist{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", collaborative='" + isCollaborative() + "'" +
            ", description='" + getDescription() + "'" +
            ", primaryColor='" + getPrimaryColor() + "'" +
            ", cover='" + getCover() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", publicAccessible='" + isPublicAccessible() + "'" +
            ", numberSongs=" + getNumberSongs() +
            ", followers=" + getFollowers() +
            ", rating=" + getRating() +
            "}";
    }
}
