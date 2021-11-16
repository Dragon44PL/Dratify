package com.musiva.tracks.track;

import com.musiva.tracks.track.dto.TrackDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrackQueryRepository {
    Optional<TrackDto> findById(UUID id);
    Optional<TrackDto> findByName(String name);
    List<TrackDto> findAll();
}