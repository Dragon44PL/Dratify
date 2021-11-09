package com.musiva.player.communcation;

import com.musiva.player.TrackData;
import com.musiva.player.VorbisTrackData;
import com.musiva.player.communication.Encoder;
import org.gagravarr.vorbis.VorbisFile;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

public class VorbisEncoder implements Encoder<VorbisTrackData, TrackData> {

    @Override
    public Optional<TrackData> encode(VorbisTrackData vorbisTrackData) {

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            VorbisFile destination = new VorbisFile(byteArrayOutputStream, vorbisTrackData.vorbisInfo(), vorbisTrackData.vorbisComments(), vorbisTrackData.vorbisSetup());
            vorbisTrackData.vorbisAudioData().forEach(destination::writeAudioData);
            destination.close();
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(new TrackData(byteArrayOutputStream.toByteArray()));
    }
}
