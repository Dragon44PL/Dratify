package com.musiva.tracks.library.event;

import com.musiva.tracks.library.vo.TrackId;

import java.time.Instant;
import java.util.UUID;

public record TrackAddedEvent(Instant occurredOn, UUID aggregateId, TrackId track) implements TrackLibraryEvent {

    public TrackAddedEvent(UUID trackLibraryId, TrackId track) {
        this(Instant.now(), trackLibraryId, track);
    }
}
