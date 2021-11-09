package com.musiva.player.repository.cache;

import com.musiva.player.repository.Filename;
import com.musiva.player.TrackData;

import java.util.Optional;

public interface TrackDataCacheRepository<T> {
    void insertTrackData(Filename filename, T trackData);
    Optional<TrackData> readTrackData(Filename filename, ByteRange range);
    void clearCache();
}
