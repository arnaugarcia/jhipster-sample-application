package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Playlist;
import com.mycompany.myapp.repository.PlaylistRepository;
import com.mycompany.myapp.service.PlaylistService;
import com.mycompany.myapp.service.dto.PlaylistDTO;
import com.mycompany.myapp.service.mapper.PlaylistMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlaylistResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlaylistResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COLLABORATIVE = false;
    private static final Boolean UPDATED_COLLABORATIVE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_COVER = "AAAAAAAAAA";
    private static final String UPDATED_COVER = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLIC_ACCESSIBLE = false;
    private static final Boolean UPDATED_PUBLIC_ACCESSIBLE = true;

    private static final Integer DEFAULT_NUMBER_SONGS = 1;
    private static final Integer UPDATED_NUMBER_SONGS = 2;

    private static final Integer DEFAULT_FOLLOWERS = 1;
    private static final Integer UPDATED_FOLLOWERS = 2;

    private static final Double DEFAULT_RATING = 1D;
    private static final Double UPDATED_RATING = 2D;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Mock
    private PlaylistRepository playlistRepositoryMock;

    @Autowired
    private PlaylistMapper playlistMapper;

    @Mock
    private PlaylistService playlistServiceMock;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaylistMockMvc;

    private Playlist playlist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Playlist createEntity(EntityManager em) {
        Playlist playlist = new Playlist()
            .name(DEFAULT_NAME)
            .collaborative(DEFAULT_COLLABORATIVE)
            .description(DEFAULT_DESCRIPTION)
            .primaryColor(DEFAULT_PRIMARY_COLOR)
            .cover(DEFAULT_COVER)
            .thumbnail(DEFAULT_THUMBNAIL)
            .publicAccessible(DEFAULT_PUBLIC_ACCESSIBLE)
            .numberSongs(DEFAULT_NUMBER_SONGS)
            .followers(DEFAULT_FOLLOWERS)
            .rating(DEFAULT_RATING);
        return playlist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Playlist createUpdatedEntity(EntityManager em) {
        Playlist playlist = new Playlist()
            .name(UPDATED_NAME)
            .collaborative(UPDATED_COLLABORATIVE)
            .description(UPDATED_DESCRIPTION)
            .primaryColor(UPDATED_PRIMARY_COLOR)
            .cover(UPDATED_COVER)
            .thumbnail(UPDATED_THUMBNAIL)
            .publicAccessible(UPDATED_PUBLIC_ACCESSIBLE)
            .numberSongs(UPDATED_NUMBER_SONGS)
            .followers(UPDATED_FOLLOWERS)
            .rating(UPDATED_RATING);
        return playlist;
    }

    @BeforeEach
    public void initTest() {
        playlist = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaylist() throws Exception {
        int databaseSizeBeforeCreate = playlistRepository.findAll().size();

        // Create the Playlist
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);
        restPlaylistMockMvc.perform(post("/api/playlists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(playlistDTO)))
            .andExpect(status().isCreated());

        // Validate the Playlist in the database
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeCreate + 1);
        Playlist testPlaylist = playlistList.get(playlistList.size() - 1);
        assertThat(testPlaylist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlaylist.isCollaborative()).isEqualTo(DEFAULT_COLLABORATIVE);
        assertThat(testPlaylist.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlaylist.getPrimaryColor()).isEqualTo(DEFAULT_PRIMARY_COLOR);
        assertThat(testPlaylist.getCover()).isEqualTo(DEFAULT_COVER);
        assertThat(testPlaylist.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testPlaylist.isPublicAccessible()).isEqualTo(DEFAULT_PUBLIC_ACCESSIBLE);
        assertThat(testPlaylist.getNumberSongs()).isEqualTo(DEFAULT_NUMBER_SONGS);
        assertThat(testPlaylist.getFollowers()).isEqualTo(DEFAULT_FOLLOWERS);
        assertThat(testPlaylist.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createPlaylistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playlistRepository.findAll().size();

        // Create the Playlist with an existing ID
        playlist.setId(1L);
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaylistMockMvc.perform(post("/api/playlists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(playlistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Playlist in the database
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlaylists() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Get all the playlistList
        restPlaylistMockMvc.perform(get("/api/playlists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playlist.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].collaborative").value(hasItem(DEFAULT_COLLABORATIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].primaryColor").value(hasItem(DEFAULT_PRIMARY_COLOR)))
            .andExpect(jsonPath("$.[*].cover").value(hasItem(DEFAULT_COVER)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].publicAccessible").value(hasItem(DEFAULT_PUBLIC_ACCESSIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].numberSongs").value(hasItem(DEFAULT_NUMBER_SONGS)))
            .andExpect(jsonPath("$.[*].followers").value(hasItem(DEFAULT_FOLLOWERS)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPlaylistsWithEagerRelationshipsIsEnabled() throws Exception {
        when(playlistServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlaylistMockMvc.perform(get("/api/playlists?eagerload=true"))
            .andExpect(status().isOk());

        verify(playlistServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPlaylistsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(playlistServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlaylistMockMvc.perform(get("/api/playlists?eagerload=true"))
            .andExpect(status().isOk());

        verify(playlistServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        // Get the playlist
        restPlaylistMockMvc.perform(get("/api/playlists/{id}", playlist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(playlist.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.collaborative").value(DEFAULT_COLLABORATIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.primaryColor").value(DEFAULT_PRIMARY_COLOR))
            .andExpect(jsonPath("$.cover").value(DEFAULT_COVER))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL))
            .andExpect(jsonPath("$.publicAccessible").value(DEFAULT_PUBLIC_ACCESSIBLE.booleanValue()))
            .andExpect(jsonPath("$.numberSongs").value(DEFAULT_NUMBER_SONGS))
            .andExpect(jsonPath("$.followers").value(DEFAULT_FOLLOWERS))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaylist() throws Exception {
        // Get the playlist
        restPlaylistMockMvc.perform(get("/api/playlists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        int databaseSizeBeforeUpdate = playlistRepository.findAll().size();

        // Update the playlist
        Playlist updatedPlaylist = playlistRepository.findById(playlist.getId()).get();
        // Disconnect from session so that the updates on updatedPlaylist are not directly saved in db
        em.detach(updatedPlaylist);
        updatedPlaylist
            .name(UPDATED_NAME)
            .collaborative(UPDATED_COLLABORATIVE)
            .description(UPDATED_DESCRIPTION)
            .primaryColor(UPDATED_PRIMARY_COLOR)
            .cover(UPDATED_COVER)
            .thumbnail(UPDATED_THUMBNAIL)
            .publicAccessible(UPDATED_PUBLIC_ACCESSIBLE)
            .numberSongs(UPDATED_NUMBER_SONGS)
            .followers(UPDATED_FOLLOWERS)
            .rating(UPDATED_RATING);
        PlaylistDTO playlistDTO = playlistMapper.toDto(updatedPlaylist);

        restPlaylistMockMvc.perform(put("/api/playlists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(playlistDTO)))
            .andExpect(status().isOk());

        // Validate the Playlist in the database
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeUpdate);
        Playlist testPlaylist = playlistList.get(playlistList.size() - 1);
        assertThat(testPlaylist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlaylist.isCollaborative()).isEqualTo(UPDATED_COLLABORATIVE);
        assertThat(testPlaylist.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlaylist.getPrimaryColor()).isEqualTo(UPDATED_PRIMARY_COLOR);
        assertThat(testPlaylist.getCover()).isEqualTo(UPDATED_COVER);
        assertThat(testPlaylist.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testPlaylist.isPublicAccessible()).isEqualTo(UPDATED_PUBLIC_ACCESSIBLE);
        assertThat(testPlaylist.getNumberSongs()).isEqualTo(UPDATED_NUMBER_SONGS);
        assertThat(testPlaylist.getFollowers()).isEqualTo(UPDATED_FOLLOWERS);
        assertThat(testPlaylist.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaylist() throws Exception {
        int databaseSizeBeforeUpdate = playlistRepository.findAll().size();

        // Create the Playlist
        PlaylistDTO playlistDTO = playlistMapper.toDto(playlist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaylistMockMvc.perform(put("/api/playlists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(playlistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Playlist in the database
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlaylist() throws Exception {
        // Initialize the database
        playlistRepository.saveAndFlush(playlist);

        int databaseSizeBeforeDelete = playlistRepository.findAll().size();

        // Delete the playlist
        restPlaylistMockMvc.perform(delete("/api/playlists/{id}", playlist.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Playlist> playlistList = playlistRepository.findAll();
        assertThat(playlistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
