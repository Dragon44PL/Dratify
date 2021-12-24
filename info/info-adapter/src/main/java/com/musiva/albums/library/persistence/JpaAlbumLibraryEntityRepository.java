package com.musiva.albums.library.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaAlbumLibraryEntityRepository extends JpaRepository<AlbumLibraryEntity, UUID> {
    Optional<AlbumLibraryEntity> findByUserId(UUID userId);
}
