package com.musiva.player.algorithm;

import java.util.List;

public interface TrackDataCacheAlgorithm {
    <T> void clearCache(List<TrackDataPlaceholder<T>> trackData, int amount);
}
