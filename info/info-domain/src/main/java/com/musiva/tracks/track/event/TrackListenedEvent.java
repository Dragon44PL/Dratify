package com.musiva.tracks.track.event;

import com.musiva.tracks.track.vo.ListeningCounter;

public record TrackListenedEvent(Instant occurredOn, UUID aggregateId, ListeningCounter listeningCounter) implements TrackEvent {

    public TrackListenedEvent(UUID songId, ListeningCounter listeningCounter) {
        this(Instant.now(), songId, listeningCounter);
    }
}
