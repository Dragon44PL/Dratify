package com.musiva.player.repository.cache;

import com.musiva.player.TrackData;
import com.musiva.player.VorbisTrackData;
import com.musiva.player.communication.Encoder;
import org.gagravarr.vorbis.VorbisAudioData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VorbisByteRangeResolver {

    private final Encoder<VorbisTrackData, TrackData> vorbisEncoder;

    public VorbisByteRangeResolver(Encoder<VorbisTrackData, TrackData> vorbisEncoder) {
        this.vorbisEncoder = vorbisEncoder;
    }

    public Optional<TrackData> resolveByteRange(VorbisTrackData vorbisTrackData, ByteRange byteRange) {

        final List<VorbisAudioData> vorbisAudioData = new ArrayList<>();

        long start = byteRange.begin();
        long end = byteRange.end();

        long current = 0;
        for(VorbisAudioData audioData : vorbisTrackData.vorbisAudioData()) {
            long length = audioData.getData().length;
            if(current > length) {

            }
        }

        final VorbisTrackData destination = new VorbisTrackData(vorbisAudioData, vorbisTrackData.vorbisInfo(), vorbisTrackData.vorbisComments(), vorbisTrackData.vorbisSetup());
        return vorbisEncoder.encode(destination);
    }
}
