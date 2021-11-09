package com.musiva.player.communcation;

import com.musiva.player.TrackData;
import com.musiva.player.VorbisTrackData;
import com.musiva.player.communication.Decoder;
import org.gagravarr.vorbis.VorbisAudioData;
import org.gagravarr.vorbis.VorbisFile;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VorbisDecoder implements Decoder<VorbisFile, VorbisTrackData> {

    @Override
    public Optional<VorbisTrackData> decode(VorbisFile vorbisFile) {

        try {

            List<VorbisAudioData> data = new ArrayList<>();
            VorbisAudioData currentPacket;

            while((currentPacket = vorbisFile.getNextAudioPacket()) != null) {
                data.add(currentPacket);
            }

            vorbisFile.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(data);
            return Optional.of(new VorbisTrackData(data, vorbisFile.getInfo(), vorbisFile.getComment(), vorbisFile.getSetup()));

        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
