package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaylistMapperTest {

    private PlaylistMapper playlistMapper;

    @BeforeEach
    public void setUp() {
        playlistMapper = new PlaylistMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(playlistMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(playlistMapper.fromId(null)).isNull();
    }
}
