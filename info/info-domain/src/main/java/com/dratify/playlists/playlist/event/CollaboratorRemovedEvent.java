package com.dratify.playlists.playlist.event;

import com.dratify.playlists.playlist.vo.UserId;
import java.time.Instant;
import java.util.UUID;

public record CollaboratorRemovedEvent(Instant occurredOn, UUID aggregateId, UserId collaborator) implements PlaylistEvent {

    public CollaboratorRemovedEvent(UUID playlistId, UserId collaborator) {
        this(Instant.now(), playlistId, collaborator);
    }
}
