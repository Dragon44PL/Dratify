package com.dratify.domain.playlists.event;

import com.dratify.domain.playlists.vo.TrackId;
import com.dratify.domain.playlists.vo.UserId;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PlaylistCreatedEvent(Instant occurredOn, UUID aggregateId, String name, UserId user, Set<TrackId> songs) implements PlaylistEvent {

    public PlaylistCreatedEvent(UUID aggregateId, String name, UserId user, Set<TrackId> songs) {
        this(Instant.now(), aggregateId, name, user, songs);
    }
}
