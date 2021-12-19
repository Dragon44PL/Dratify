package com.musiva.tracks.library.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaTrackLibraryRepository extends JpaRepository<TrackLibraryEntity, UUID> {
    Optional<TrackLibraryEntity> findByUserId(UUID userId);
}
