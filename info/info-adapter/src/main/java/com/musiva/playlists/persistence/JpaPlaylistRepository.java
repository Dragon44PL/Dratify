package com.musiva.playlists.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaPlaylistRepository extends JpaRepository<PlaylistEntity, UUID> {
    Optional<PlaylistEntity> findByName(String name);
}
