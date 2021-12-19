package com.musiva.tracks.library;

import com.musiva.tracks.library.commands.CreateTrackLibraryCommand;
import com.musiva.tracks.library.commands.InsertTrackCommand;
import com.musiva.tracks.library.event.TrackLibraryEvent;
import com.musiva.tracks.library.exception.TrackLibraryAlreadyDefinedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrackLibraryFacadeProxy extends TrackLibraryFacade {

    public TrackLibraryFacadeProxy(TrackLibraryRepository trackLibraryRepository) {
        super(trackLibraryRepository);
    }

    @Override
    @Transactional
    public List<TrackLibraryEvent> createTrackLibrary(CreateTrackLibraryCommand createTrackLibraryCommand) throws TrackLibraryAlreadyDefinedException {
        return super.createTrackLibrary(createTrackLibraryCommand);
    }

    @Override
    @Transactional
    public List<TrackLibraryEvent> insertTrack(InsertTrackCommand insertTrackCommand) {
        return super.insertTrack(insertTrackCommand);
    }
}
