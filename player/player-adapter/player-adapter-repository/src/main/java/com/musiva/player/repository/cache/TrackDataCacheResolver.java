package com.musiva.player.repository.cache;

import com.musiva.player.TrackData;
import com.musiva.player.VorbisTrackData;
import com.musiva.player.algorithm.TrackDataCacheAlgorithm;
import com.musiva.player.algorithm.TrackDataPlaceholder;
import com.musiva.player.repository.Filename;

import java.util.*;

public class TrackDataCacheResolver implements TrackDataCacheRepository<VorbisTrackData> {

    private final List<TrackDataPlaceholder<VorbisTrackData>> trackData;
    private final VorbisByteRangeResolver vorbisByteRangeResolver;
    private TrackDataCacheProperties trackDataCacheProperties;

    public TrackDataCacheResolver(VorbisByteRangeResolver vorbisByteRangeResolver, TrackDataCacheProperties trackDataCacheProperties) {
        this.vorbisByteRangeResolver = vorbisByteRangeResolver;
        this.trackDataCacheProperties = trackDataCacheProperties;
        this.trackData = new ArrayList<>();
    }

    public void changeTrackDataCacheProperties(TrackDataCacheProperties trackDataCacheProperties) {
        this.trackDataCacheProperties = trackDataCacheProperties;
    }

    @Override
    public void insertTrackData(Filename filename, VorbisTrackData trackData) {
        this.trackData.add(new TrackDataPlaceholder<>(filename, trackData));
    }

    @Override
    public Optional<TrackData> readTrackData(Filename filename, ByteRange range) {
        var data = trackData.stream().filter(t -> t.filename().equals(filename)).findAny();
        return data.flatMap(found -> resolveTrackData(found, range));
    }

    private Optional<TrackData> resolveTrackData(TrackDataPlaceholder<VorbisTrackData> trackDataPlaceholder, ByteRange range) {
        trackDataPlaceholder.triggerRequested();
        return vorbisByteRangeResolver.resolveByteRange(trackDataPlaceholder.trackData(), range);
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
