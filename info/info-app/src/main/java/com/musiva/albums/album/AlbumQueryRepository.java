package com.musiva.albums.album;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumQueryRepository {
    Optional<Album> findById(UUID id);
    List<Album> findAll();
}
