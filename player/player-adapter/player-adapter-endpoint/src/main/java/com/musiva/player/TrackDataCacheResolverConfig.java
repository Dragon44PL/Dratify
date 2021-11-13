package com.musiva.player;

import com.musiva.player.algorithm.ClearOldestTracksAlgorithm;
import com.musiva.player.algorithm.TrackDataCacheAlgorithm;
import com.musiva.player.communcation.VorbisDecoder;
import com.musiva.player.communcation.VorbisEncoder;
import com.musiva.player.communication.Decoder;
import com.musiva.player.communication.Encoder;
import com.musiva.player.repository.TrackDataRepository;
import com.musiva.player.repository.VorbisDataRepository;
import com.musiva.player.repository.cache.TrackDataCacheProperties;
import com.musiva.player.repository.cache.TrackDataCacheResolver;
import com.musiva.player.repository.cache.VorbisByteRangeResolver;
import org.gagravarr.vorbis.VorbisFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TrackDataCacheResolverConfig {

    @Value("${player.tracks.amount}")
    private int MAX_TRACKS_AMOUNT;

    @Bean
    TrackDataCacheAlgorithm clearOldestTracksAlgorithm() {
        return new ClearOldestTracksAlgorithm();
    }

    @Bean
    TrackDataRepository<?> trackDataRepository() {
        return new VorbisDataRepository(decoder());
    }

    @Bean
    Decoder<VorbisFile, VorbisTrackData> decoder() {
        return new VorbisDecoder();
    }

    @Bean
    Encoder<VorbisTrackData, TrackData> encoder() {
        return new VorbisEncoder();
    }

    VorbisByteRangeResolver vorbisByteRangeResolver() {
        return new VorbisByteRangeResolver(encoder());
    }

    @Bean
    TrackDataCacheResolver trackDataCacheResolver(TrackDataCacheAlgorithm trackDataCacheAlgorithm) {
        final TrackDataCacheProperties trackDataCacheProperties = new TrackDataCacheProperties(MAX_TRACKS_AMOUNT, trackDataCacheAlgorithm);
        return new TrackDataCacheResolver(vorbisByteRangeResolver(), trackDataCacheProperties);
    }
}
