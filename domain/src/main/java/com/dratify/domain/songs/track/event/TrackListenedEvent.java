package com.dratify.domain.songs.track.event;

import com.dratify.domain.songs.track.vo.ListeningCounter;
import java.time.Instant;
import java.util.UUID;

public record TrackListenedEvent(Instant occurredOn, UUID aggregateId, ListeningCounter listeningCounter) implements TrackEvent {

    public TrackListenedEvent(UUID songId, ListeningCounter listeningCounter) {
        this(Instant.now(), songId, listeningCounter);
    }
}
