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
        long start, end = 0;

        for(VorbisAudioData audioData : vorbisTrackData.vorbisAudioData()) {
            long currentPacketLength = audioData.getData().length;
            start = end;
            end += currentPacketLength;
            int beginDiff = 0, endDiff = 0;

            if(start >= byteRange.begin()) {
                beginDiff = (int) Math.abs(start - byteRange.begin());
            }

            if(end >= byteRange.end()) {
                endDiff = (int) Math.abs(end - byteRange.end());
            }

            if(beginDiff - endDiff > 0) {
                VorbisAudioData created = resolveAudioData(audioData, beginDiff, endDiff);
                vorbisAudioData.add(created);
            }
        }

        final VorbisTrackData destination = new VorbisTrackData(vorbisAudioData, vorbisTrackData.vorbisInfo(), vorbisTrackData.vorbisComments(), vorbisTrackData.vorbisSetup());
        return vorbisEncoder.encode(destination);
    }

    private VorbisAudioData resolveAudioData(VorbisAudioData vorbisAudioData, int beginOffset, int endOffset) {
        byte[] vorbisBytes = vorbisAudioData.getData();
        int length = vorbisBytes.length;

        byte[] bytes = new byte[length - beginOffset - endOffset];
        if (length - endOffset - beginOffset - 1 >= 0) {
            System.arraycopy(vorbisBytes, beginOffset - 1, bytes, beginOffset - 1, length - endOffset - beginOffset - 1);
        }

        return new VorbisAudioData(bytes);
    }
}
