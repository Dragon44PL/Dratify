package com.musiva.tracks.library.event;

import com.musiva.tracks.library.vo.TrackId;
import com.musiva.tracks.library.vo.UserId;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record TrackLibraryCreatedEvent(Instant occurredOn, UUID aggregateId, UserId user, Set<TrackId> tracks) implements TrackLibraryEvent {

    public TrackLibraryCreatedEvent(UUID trackLibraryId, UserId user, Set<TrackId> tracks) {
        this(Instant.now(), trackLibraryId, user, tracks);
    }
}
