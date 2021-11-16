package com.musiva.playlists.playlist.event;

import com.musiva.playlists.playlist.vo.TrackId;
import com.musiva.playlists.playlist.vo.UserId;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PlaylistCreatedEvent(Instant occurredOn, UUID aggregateId, String name, UserId author, Set<UserId> collaborators, Set<TrackId> songs) implements PlaylistEvent {

    public PlaylistCreatedEvent(UUID aggregateId, String name, UserId user, Set<UserId> collaborators, Set<TrackId> songs) {
        this(Instant.now(), aggregateId, name, user, collaborators, songs);
    }
}
