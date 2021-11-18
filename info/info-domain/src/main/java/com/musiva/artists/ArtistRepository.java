package com.musiva.artists;

import domain.DomainRepository;

import java.util.Optional;
import java.util.UUID;

interface ArtistRepository extends DomainRepository<UUID, Artist> {
    Optional<Artist> findByName(String name);
}
