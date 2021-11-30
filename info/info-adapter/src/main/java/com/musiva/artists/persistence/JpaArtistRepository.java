package com.musiva.artists.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaArtistRepository extends JpaRepository<ArtistEntity, UUID> {
    Optional<ArtistEntity> findByName(String name);
}
