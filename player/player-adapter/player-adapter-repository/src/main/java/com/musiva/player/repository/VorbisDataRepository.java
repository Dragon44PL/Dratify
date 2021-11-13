package com.musiva.player.repository;

import com.musiva.player.VorbisTrackData;
import com.musiva.player.communication.Decoder;
import org.gagravarr.vorbis.VorbisFile;

import java.io.File;
import java.util.Optional;

public class VorbisDataRepository implements TrackDataRepository<VorbisTrackData> {

    private final Decoder<VorbisFile, VorbisTrackData> vorbisDecoder;

    public VorbisDataRepository(Decoder<VorbisFile, VorbisTrackData>  vorbisDecoder) {
        this.vorbisDecoder = vorbisDecoder;
    }

    @Override
    public Optional<VorbisTrackData> findByFilename(Filename filename) {
        try(VorbisFile vorbisFile = new VorbisFile(new File(filename.toString()))) {
            return vorbisDecoder.decode(vorbisFile);
        } catch (Exception e ) {
            return Optional.empty();
        }
    }

}
