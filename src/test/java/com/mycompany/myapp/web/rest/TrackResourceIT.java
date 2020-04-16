package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Track;
import com.mycompany.myapp.repository.TrackRepository;
import com.mycompany.myapp.service.TrackService;
import com.mycompany.myapp.service.dto.TrackDTO;
import com.mycompany.myapp.service.mapper.TrackMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrackResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TrackResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_RATING = 1D;
    private static final Double UPDATED_RATING = 2D;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_POPULARITY = "AAAAAAAAAA";
    private static final String UPDATED_POPULARITY = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RELEASED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RELEASED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TrackMapper trackMapper;

    @Autowired
    private TrackService trackService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrackMockMvc;

    private Track track;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Track createEntity(EntityManager em) {
        Track track = new Track()
            .name(DEFAULT_NAME)
            .rating(DEFAULT_RATING)
            .url(DEFAULT_URL)
            .popularity(DEFAULT_POPULARITY)
            .thumbnail(DEFAULT_THUMBNAIL)
            .createdAt(DEFAULT_CREATED_AT)
            .released(DEFAULT_RELEASED)
            .duration(DEFAULT_DURATION)
            .color(DEFAULT_COLOR);
        return track;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Track createUpdatedEntity(EntityManager em) {
        Track track = new Track()
            .name(UPDATED_NAME)
            .rating(UPDATED_RATING)
            .url(UPDATED_URL)
            .popularity(UPDATED_POPULARITY)
            .thumbnail(UPDATED_THUMBNAIL)
            .createdAt(UPDATED_CREATED_AT)
            .released(UPDATED_RELEASED)
            .duration(UPDATED_DURATION)
            .color(UPDATED_COLOR);
        return track;
    }

    @BeforeEach
    public void initTest() {
        track = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrack() throws Exception {
        int databaseSizeBeforeCreate = trackRepository.findAll().size();

        // Create the Track
        TrackDTO trackDTO = trackMapper.toDto(track);
        restTrackMockMvc.perform(post("/api/tracks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackDTO)))
            .andExpect(status().isCreated());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeCreate + 1);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrack.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testTrack.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testTrack.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
        assertThat(testTrack.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testTrack.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTrack.getReleased()).isEqualTo(DEFAULT_RELEASED);
        assertThat(testTrack.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTrack.getColor()).isEqualTo(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    public void createTrackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trackRepository.findAll().size();

        // Create the Track with an existing ID
        track.setId(1L);
        TrackDTO trackDTO = trackMapper.toDto(track);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackMockMvc.perform(post("/api/tracks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTracks() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        // Get all the trackList
        restTrackMockMvc.perform(get("/api/tracks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(track.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].released").value(hasItem(sameInstant(DEFAULT_RELEASED))))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)));
    }
    
    @Test
    @Transactional
    public void getTrack() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        // Get the track
        restTrackMockMvc.perform(get("/api/tracks/{id}", track.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(track.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.released").value(sameInstant(DEFAULT_RELEASED)))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR));
    }

    @Test
    @Transactional
    public void getNonExistingTrack() throws Exception {
        // Get the track
        restTrackMockMvc.perform(get("/api/tracks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrack() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        int databaseSizeBeforeUpdate = trackRepository.findAll().size();

        // Update the track
        Track updatedTrack = trackRepository.findById(track.getId()).get();
        // Disconnect from session so that the updates on updatedTrack are not directly saved in db
        em.detach(updatedTrack);
        updatedTrack
            .name(UPDATED_NAME)
            .rating(UPDATED_RATING)
            .url(UPDATED_URL)
            .popularity(UPDATED_POPULARITY)
            .thumbnail(UPDATED_THUMBNAIL)
            .createdAt(UPDATED_CREATED_AT)
            .released(UPDATED_RELEASED)
            .duration(UPDATED_DURATION)
            .color(UPDATED_COLOR);
        TrackDTO trackDTO = trackMapper.toDto(updatedTrack);

        restTrackMockMvc.perform(put("/api/tracks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackDTO)))
            .andExpect(status().isOk());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrack.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testTrack.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTrack.getPopularity()).isEqualTo(UPDATED_POPULARITY);
        assertThat(testTrack.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testTrack.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTrack.getReleased()).isEqualTo(UPDATED_RELEASED);
        assertThat(testTrack.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTrack.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingTrack() throws Exception {
        int databaseSizeBeforeUpdate = trackRepository.findAll().size();

        // Create the Track
        TrackDTO trackDTO = trackMapper.toDto(track);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackMockMvc.perform(put("/api/tracks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrack() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        int databaseSizeBeforeDelete = trackRepository.findAll().size();

        // Delete the track
        restTrackMockMvc.perform(delete("/api/tracks/{id}", track.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
