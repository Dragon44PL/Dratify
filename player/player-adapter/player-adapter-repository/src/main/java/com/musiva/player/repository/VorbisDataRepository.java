package com.musiva.player.repository;

import com.musiva.player.TrackData;
import org.gagravarr.vorbis.VorbisAudioData;
import org.gagravarr.vorbis.VorbisFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VorbisDataRepository implements TrackDataRepository {

    @Override
    public Optional<TrackData> findByFilename(Filename filename) {

        try {

            VorbisFile vorbisFile = new VorbisFile(new File(filename.toString()));
            List<VorbisAudioData> data = new ArrayList<>();
            VorbisAudioData currentPacket;

            while((currentPacket = vorbisFile.getNextAudioPacket()) != null) {
                data.add(currentPacket);
            }

            vorbisFile.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(data);
            return Optional.of(new TrackData(byteArrayOutputStream.toByteArray()));

        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
