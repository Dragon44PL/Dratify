package com.musiva.playlists.playlist.event;

import com.musiva.playlists.playlist.vo.UserId;
import java.time.Instant;
import java.util.UUID;

public record CollaboratorAddedEvent(Instant occurredOn, UUID aggregateId, UserId collaborator) implements PlaylistEvent {

    public CollaboratorAddedEvent(UUID playlistId, UserId collaborator) {
        this(Instant.now(), playlistId, collaborator);
    }
}
