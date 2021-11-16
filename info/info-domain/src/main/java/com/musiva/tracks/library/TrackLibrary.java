package com.musiva.tracks.library;

import com.musiva.tracks.library.event.TrackAddedEvent;
import com.musiva.tracks.library.event.TrackLibraryCreatedEvent;
import com.musiva.tracks.library.event.TrackLibraryEvent;
import com.musiva.tracks.library.event.TrackRemovedEvent;
import com.musiva.tracks.library.vo.TrackId;
import com.musiva.tracks.library.vo.UserId;
import domain.AggregateRoot;
import java.util.*;

class TrackLibrary extends AggregateRoot<UUID, TrackLibraryEvent> {

    private final UUID id;
    private final UserId user;
    private final Set<TrackId> tracks;

    static TrackLibrary create(UUID id, UserId user, Set<TrackId> tracks) {
        final TrackLibrary trackLibrary = new TrackLibrary(id, user, tracks, new ArrayList<>());
        trackLibrary.registerEvent(new TrackLibraryCreatedEvent(trackLibrary.id, trackLibrary.user, trackLibrary.tracks));
        return trackLibrary;
    }

    static TrackLibrary restore(UUID id, UserId user, Set<TrackId> tracks) {
        return new TrackLibrary(id, user, tracks, new ArrayList<>());
    }

    private TrackLibrary(UUID id, UserId user, Set<TrackId> tracks, List<TrackLibraryEvent> events) {
        super(events);
        this.id = id;
        this.user = user;
        this.tracks = new HashSet<>(tracks);
    }

    void addTrack(TrackId track) {
        if(!hasTrack(track)) {
            processAddingTrack(track);
        }
    }

    private void processAddingTrack(TrackId track) {
        tracks.add(track);
        final TrackAddedEvent trackAddedEvent = new TrackAddedEvent(id, track);
        this.registerEvent(trackAddedEvent);
    }

    void removeTrack(TrackId track) {
        if(hasTrack(track)) {
            processRemovingTrack(track);
        }
    }

    private void processRemovingTrack(TrackId track) {
        tracks.remove(track);
        final TrackRemovedEvent trackRemovedEvent = new TrackRemovedEvent(id, track);
        this.registerEvent(trackRemovedEvent);
    }

    boolean hasTrack(TrackId track) {
        return tracks.contains(track);
    }
}
