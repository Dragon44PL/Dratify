package com.musiva.tracks.track.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaTrackRepository extends JpaRepository<TrackEntity, UUID> {
    Optional<TrackEntity> findByName(String name);
}
