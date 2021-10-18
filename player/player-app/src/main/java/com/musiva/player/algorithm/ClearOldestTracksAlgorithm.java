package com.musiva.player.algorithm;

import com.musiva.player.TrackDataPlaceholder;

import java.util.Comparator;
import java.util.List;

public class ClearOldestTracksAlgorithm implements TrackDataCacheAlgorithm {

    @Override
    public void clearTrackData(List<TrackDataPlaceholder> trackData, int amount) {
        trackData.sort(Comparator.comparing(TrackDataPlaceholder::occurredOn));
        int amountToDelete = Math.min(amount, trackData.size());
        trackData.subList(0, amountToDelete).clear();
    }
}