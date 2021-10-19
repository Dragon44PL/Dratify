package com.musiva.player.repository.cache;

import com.musiva.player.repository.Filename;
import com.musiva.player.TrackData;

public interface CacheRepository {
    TrackData readTrackData(Filename filename, ByteRange range);
}
