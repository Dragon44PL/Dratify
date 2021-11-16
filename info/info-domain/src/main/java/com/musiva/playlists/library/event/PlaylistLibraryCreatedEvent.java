package com.musiva.playlists.library.event;

import com.musiva.playlists.library.vo.PlaylistId;
import com.musiva.playlists.library.vo.UserId;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PlaylistLibraryCreatedEvent(Instant occurredOn, UUID aggregateId, UserId user, Set<PlaylistId> playlists) implements PlaylistLibraryEvent {

    public PlaylistLibraryCreatedEvent(UUID aggregateId, UserId user, Set<PlaylistId> playlists) {
        this(Instant.now(), aggregateId, user, playlists);
    }
}
