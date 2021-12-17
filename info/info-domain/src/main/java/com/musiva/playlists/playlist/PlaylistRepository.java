package com.musiva.playlists.playlist;

import domain.DomainRepository;

import java.util.Optional;
import java.util.UUID;

interface PlaylistRepository extends DomainRepository<UUID, Playlist> {
    Optional<Playlist> findByName(String name);
}
