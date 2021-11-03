package com.musiva.player.repository.cache;

import com.musiva.player.algorithm.TrackDataCacheAlgorithm;

public record TrackDataCacheProperties(int totalTrackAmount, TrackDataCacheAlgorithm trackDataCacheAlgorithm) { }
