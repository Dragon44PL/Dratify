package com.musiva.playlists;

import com.musiva.playlists.dto.PlaylistDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaylistQueryRepository {
    Optional<PlaylistDto> findById(UUID id);
    List<PlaylistDto> findAll();
}
