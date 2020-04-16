package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TrackDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Track}.
 */
public interface TrackService {

    /**
     * Save a track.
     *
     * @param trackDTO the entity to save.
     * @return the persisted entity.
     */
    TrackDTO save(TrackDTO trackDTO);

    /**
     * Get all the tracks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrackDTO> findAll(Pageable pageable);

    /**
     * Get the "id" track.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrackDTO> findOne(Long id);

    /**
     * Delete the "id" track.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
