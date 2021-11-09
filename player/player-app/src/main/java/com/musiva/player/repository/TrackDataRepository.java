package com.musiva.player.repository;

import java.util.Optional;

public interface TrackDataRepository<T> {
    Optional<T> findByFilename(Filename filename);
}
