package com.dratify.domain.songs.library.event;

import com.dratify.domain.songs.library.vo.TrackId;
import java.time.Instant;
import java.util.UUID;

public record TrackRemovedEvent(Instant occurredOn, UUID aggregateId, TrackId track) implements TrackLibraryEvent {

    public TrackRemovedEvent(UUID trackLibraryId, TrackId track) {
        this(Instant.now(), trackLibraryId, track);
    }
}
