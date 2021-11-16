package com.musiva.artists;

import com.musiva.artists.dto.ArtistDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtistQueryRepository {
    Optional<ArtistDto> findById(UUID id);
    Optional<ArtistDto> findByName(String name);
    List<ArtistDto> findAll();
}