package com.musiva.player.repository;

import com.musiva.player.TrackData;

import java.util.Optional;

public interface TrackDataRepository {
    Optional<TrackData> findByFilename(Filename filename);
}
