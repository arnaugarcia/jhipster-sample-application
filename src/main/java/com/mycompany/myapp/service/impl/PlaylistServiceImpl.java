package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PlaylistService;
import com.mycompany.myapp.domain.Playlist;
import com.mycompany.myapp.repository.PlaylistRepository;
import com.mycompany.myapp.service.dto.PlaylistDTO;
import com.mycompany.myapp.service.mapper.PlaylistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Playlist}.
 */
@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

    private final Logger log = LoggerFactory.getLogger(PlaylistServiceImpl.class);

    private final PlaylistRepository playlistRepository;

    private final PlaylistMapper playlistMapper;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
    }

    /**
     * Save a playlist.
     *
     * @param playlistDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PlaylistDTO save(PlaylistDTO playlistDTO) {
        log.debug("Request to save Playlist : {}", playlistDTO);
        Playlist playlist = playlistMapper.toEntity(playlistDTO);
        playlist = playlistRepository.save(playlist);
        return playlistMapper.toDto(playlist);
    }

    /**
     * Get all the playlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlaylistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Playlists");
        return playlistRepository.findAll(pageable)
            .map(playlistMapper::toDto);
    }

    /**
     * Get all the playlists with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PlaylistDTO> findAllWithEagerRelationships(Pageable pageable) {
        return playlistRepository.findAllWithEagerRelationships(pageable).map(playlistMapper::toDto);
    }

    /**
     * Get one playlist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlaylistDTO> findOne(Long id) {
        log.debug("Request to get Playlist : {}", id);
        return playlistRepository.findOneWithEagerRelationships(id)
            .map(playlistMapper::toDto);
    }

    /**
     * Delete the playlist by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Playlist : {}", id);
        playlistRepository.deleteById(id);
    }
}
