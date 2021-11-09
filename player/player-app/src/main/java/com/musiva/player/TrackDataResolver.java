package com.musiva.player;

import com.musiva.player.repository.Filename;
import com.musiva.player.repository.TrackDataRepository;
import com.musiva.player.repository.cache.ByteRange;
import com.musiva.player.repository.cache.TrackDataCacheRepository;

import java.util.Optional;

public final class TrackDataResolver<T> {

    private final TrackDataRepository<T> trackDataRepository;
    private final TrackDataCacheRepository<T> trackDataCacheRepository;

    public TrackDataResolver(TrackDataRepository<T> trackDataRepository, TrackDataCacheRepository<T> trackDataCacheRepository) {
        this.trackDataRepository = trackDataRepository;
        this.trackDataCacheRepository = trackDataCacheRepository;
    }

    public final Optional<TrackData> resolveTrackData(final Filename filename, final ByteRange byteRange) {
        final Optional<TrackData> trackData = trackDataCacheRepository.readTrackData(filename, byteRange);
        return trackData.isPresent() ? trackData : resolveTrackDataFromFileSystem(filename, byteRange);
    }

    private Optional<TrackData> resolveTrackDataFromFileSystem(Filename filename, ByteRange byteRange) {
        final Optional<T> found = trackDataRepository.findByFilename(filename);
        return found.map(data -> {
            trackDataCacheRepository.clearCache();
            trackDataCacheRepository.insertTrackData(filename, data);
            return trackDataCacheRepository.readTrackData(filename, byteRange);
        }).orElse(Optional.empty());
    }

}
