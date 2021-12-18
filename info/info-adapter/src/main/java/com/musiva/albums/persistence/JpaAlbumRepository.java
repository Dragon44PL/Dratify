package com.musiva.albums.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaAlbumRepository extends JpaRepository<AlbumEntity, UUID> {
    Optional<AlbumEntity> findByNameAndArtistId(String name, UUID artistId);
}