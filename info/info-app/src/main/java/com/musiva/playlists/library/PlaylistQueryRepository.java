package com.musiva.playlists.library;

import com.musiva.playlists.library.dto.PlaylistLibraryDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaylistQueryRepository {
    Optional<PlaylistLibraryDto> findById(UUID id);
    List<PlaylistLibraryDto> findAll();
}
