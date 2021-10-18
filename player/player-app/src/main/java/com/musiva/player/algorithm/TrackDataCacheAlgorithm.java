package com.musiva.player.algorithm;

import com.musiva.player.TrackDataPlaceholder;
import java.util.List;

public interface TrackDataCacheAlgorithm {
    void clearTrackData(List<TrackDataPlaceholder> trackData, int amount);
}
