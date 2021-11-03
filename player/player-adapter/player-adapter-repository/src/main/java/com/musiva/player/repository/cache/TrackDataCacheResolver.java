package com.musiva.player.repository.cache;

import com.musiva.player.TrackData;
import com.musiva.player.algorithm.TrackDataCacheAlgorithm;
import com.musiva.player.algorithm.TrackDataPlaceholder;
import com.musiva.player.repository.Filename;

import java.util.*;

public class TrackDataCacheResolver implements TrackDataCacheRepository {

    private final List<TrackDataPlaceholder> trackData;
    private TrackDataCacheProperties trackDataCacheProperties;

    TrackDataCacheResolver(TrackDataCacheProperties trackDataCacheProperties) {
        this.trackDataCacheProperties = trackDataCacheProperties;
        this.trackData = new ArrayList<>();
    }

    void changeTrackDataCacheProperties(TrackDataCacheProperties trackDataCacheProperties) {
        this.trackDataCacheProperties = trackDataCacheProperties;
    }

    @Override
    public void insertTrackData(Filename filename, TrackData trackData) {
        this.trackData.add(new TrackDataPlaceholder(filename, trackData));
    }

    @Override
    public Optional<TrackData> readTrackData(Filename filename, ByteRange range) {
        final Optional<TrackDataPlaceholder> data = trackData.stream().filter(t -> t.filename().equals(filename)).findAny();
        return data.flatMap(found -> resolveTrackData(found, range));
    }

    private Optional<TrackData> resolveTrackData(TrackDataPlaceholder trackDataPlaceholder, ByteRange range) {
        trackDataPlaceholder.triggerRequested();
        return Optional.of(trackDataPlaceholder.trackData());
    }

    @Override
    public void clearCache() {
        final int totalAmount = trackDataCacheProperties.totalTrackAmount();
        if(totalAmount < trackData.size()) {
            final TrackDataCacheAlgorithm algorithm = trackDataCacheProperties.trackDataCacheAlgorithm();
            algorithm.clearTrackData(trackData, trackData.size() - totalAmount);
        }
    }
}
