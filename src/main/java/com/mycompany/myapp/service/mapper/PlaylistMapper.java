package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PlaylistDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Playlist} and its DTO {@link PlaylistDTO}.
 */
@Mapper(componentModel = "spring", uses = {TrackMapper.class})
public interface PlaylistMapper extends EntityMapper<PlaylistDTO, Playlist> {


    @Mapping(target = "removeTrack", ignore = true)

    default Playlist fromId(Long id) {
        if (id == null) {
            return null;
        }
        Playlist playlist = new Playlist();
        playlist.setId(id);
        return playlist;
    }
}
