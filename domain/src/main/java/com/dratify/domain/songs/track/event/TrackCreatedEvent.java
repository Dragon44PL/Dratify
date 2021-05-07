package com.dratify.domain.songs.track.event;

import com.dratify.domain.songs.track.vo.ListeningCounter;
import com.dratify.domain.songs.track.vo.TrackDataPath;
import java.time.Instant;
import java.util.UUID;

public record TrackCreatedEvent(Instant occurredOn, UUID aggregateId, String name, TrackDataPath trackDataPath, ListeningCounter listeningCounter) implements TrackEvent {

    public TrackCreatedEvent(UUID songId, String name, TrackDataPath trackDataPath, ListeningCounter listeningCounter) {
        this(Instant.now(), songId, name, trackDataPath, listeningCounter);
    }
}
