package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrackMapperTest {

    private TrackMapper trackMapper;

    @BeforeEach
    public void setUp() {
        trackMapper = new TrackMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(trackMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(trackMapper.fromId(null)).isNull();
    }
}
