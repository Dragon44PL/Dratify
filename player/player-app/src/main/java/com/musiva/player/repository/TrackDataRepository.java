package com.musiva.player.repository;

import com.musiva.player.TrackData;

public interface TrackDataRepository {
    TrackData findByFilename(Filename filename);
}
