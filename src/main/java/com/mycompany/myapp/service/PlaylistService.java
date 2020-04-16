package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PlaylistDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Playlist}.
 */
public interface PlaylistService {

    /**
     * Save a playlist.
     *
     * @param playlistDTO the entity to save.
     * @return the persisted entity.
     */
    PlaylistDTO save(PlaylistDTO playlistDTO);

    /**
     * Get all the playlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlaylistDTO> findAll(Pageable pageable);

    /**
     * Get all the playlists with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PlaylistDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" playlist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlaylistDTO> findOne(Long id);

    /**
     * Delete the "id" playlist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
