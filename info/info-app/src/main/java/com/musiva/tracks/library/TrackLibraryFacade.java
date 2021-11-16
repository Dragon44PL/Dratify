package com.musiva.tracks.library;

import com.musiva.tracks.library.commands.CreateTrackLibraryCommand;
import com.musiva.tracks.library.commands.InsertTrackCommand;
import com.musiva.tracks.library.event.TrackLibraryEvent;
import com.musiva.tracks.library.exception.TrackLibraryAlreadyDefinedException;
import java.util.*;

public class TrackLibraryFacade {

    private final TrackLibraryRepository trackLibraryRepository;

    public TrackLibraryFacade(TrackLibraryRepository trackLibraryRepository) {
        this.trackLibraryRepository = trackLibraryRepository;
    }

    public List<TrackLibraryEvent> createTrackLibrary(CreateTrackLibraryCommand createTrackLibraryCommand) throws TrackLibraryAlreadyDefinedException {
        Optional<TrackLibrary> library = trackLibraryRepository.findByUserId(createTrackLibraryCommand.user());

        if(library.isPresent()) {
            throw new TrackLibraryAlreadyDefinedException("TrackLibrary for '" + createTrackLibraryCommand.user() + "' already defined");
        }

        final UUID randomId = UUID.randomUUID();
        final TrackLibrary createdLibrary = TrackLibrary.create(randomId, createTrackLibraryCommand.user(), new HashSet<>());
        trackLibraryRepository.save(createdLibrary);
        return createdLibrary.events();
    }

    public List<TrackLibraryEvent> insertTrack(InsertTrackCommand insertTrackCommand) {
        Optional<TrackLibrary> library = trackLibraryRepository.findById(insertTrackCommand.id());
        return library.map(trackLibrary -> processInsertingTrack(trackLibrary, insertTrackCommand)).orElseGet(ArrayList::new);
    }

    private List<TrackLibraryEvent> processInsertingTrack(TrackLibrary trackLibrary, InsertTrackCommand insertTrackCommand) {
        trackLibrary.addTrack(insertTrackCommand.trackId());
        trackLibraryRepository.save(trackLibrary);
        return trackLibrary.events();
    }

}
