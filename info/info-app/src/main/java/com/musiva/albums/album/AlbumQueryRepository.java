package com.musiva.albums.album;

import com.musiva.albums.album.dto.AlbumDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumQueryRepository {
    Optional<AlbumDto> findById(UUID id);
    List<AlbumDto> findAll();
}
