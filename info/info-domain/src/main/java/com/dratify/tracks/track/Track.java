package com.dratify.tracks.track;

import com.dratify.tracks.track.event.TrackCreatedEvent;
import com.dratify.tracks.track.event.TrackDataPathChangedEvent;
import com.dratify.tracks.track.event.TrackEvent;
import com.dratify.tracks.track.event.TrackListenedEvent;
import com.dratify.tracks.track.vo.ListeningCounter;
import com.dratify.tracks.track.vo.TrackDataPath;
import domain.AggregateRoot;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Track extends AggregateRoot<UUID, TrackEvent> {

    private final UUID id;
    private final String name;
    private TrackDataPath trackDataPath;
    private ListeningCounter listeningCounter;

    static Track create(UUID id, String name, TrackDataPath path) {
        final Track track = new Track(id, name, path, ListeningCounter.zero(), new ArrayList<>());
        track.registerEvent(new TrackCreatedEvent(track.id, track.name, track.trackDataPath, track.listeningCounter));
        return track;
    }

    static Track restore(UUID id, String name, TrackDataPath path, ListeningCounter listeningCounter) {
        return new Track(id, name, path, listeningCounter, new ArrayList<>());
    }

    private Track(UUID id, String name, TrackDataPath trackDataPath, ListeningCounter listeningCounter, List<TrackEvent> events) {
        super(events);
        this.id = id;
        this.name = name;
        this.trackDataPath = trackDataPath;
        this.listeningCounter = listeningCounter;
    }

    void trackListened() {
        this.listeningCounter = listeningCounter.increment();
        final TrackListenedEvent trackListenedEvent = new TrackListenedEvent(id, listeningCounter);
        registerEvent(trackListenedEvent);
    }

    void changeTrackDataPath(TrackDataPath candidate) {
        if(!sameTrackDataPath(candidate)) {
            this.trackDataPath = candidate;
            final TrackDataPathChangedEvent trackDataPathChangedEvent = new TrackDataPathChangedEvent(id, this.trackDataPath);
            this.registerEvent(trackDataPathChangedEvent);
        }
    }

    private boolean sameTrackDataPath(TrackDataPath candidate) {
        return trackDataPath.equals(candidate);
    }

}
