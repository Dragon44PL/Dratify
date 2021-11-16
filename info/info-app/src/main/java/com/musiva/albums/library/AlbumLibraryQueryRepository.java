package com.musiva.albums.library;

import com.musiva.albums.library.dto.AlbumLibraryDto;
import com.musiva.albums.library.vo.UserId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumLibraryQueryRepository {
    Optional<AlbumLibraryDto> findById(UUID id);
    Optional<AlbumLibraryDto> findByUser(UserId userId);
    List<AlbumLibraryDto> findAll();
}
