package com.musiva.tracks.library;

import com.musiva.tracks.library.vo.UserId;
import domain.DomainRepository;

import java.util.Optional;
import java.util.UUID;

interface TrackLibraryRepository extends DomainRepository<UUID, TrackLibrary> {
    Optional<TrackLibrary> findByUserId(UserId userId);
}
