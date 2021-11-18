package com.musiva.tracks.track;

import domain.DomainRepository;

import java.util.Optional;
import java.util.UUID;

interface TrackRepository extends DomainRepository<UUID, Track> {
    Optional<Track> findByName(String name);
}
