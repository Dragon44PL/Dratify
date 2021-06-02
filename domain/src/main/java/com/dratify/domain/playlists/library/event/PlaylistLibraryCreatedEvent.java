package com.dratify.domain.playlists.library.event;

import com.dratify.domain.playlists.library.vo.PlaylistId;
import com.dratify.domain.playlists.library.vo.UserId;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PlaylistLibraryCreatedEvent(Instant occurredOn, UUID aggregateId, UserId user, Set<PlaylistId> playlists) implements PlaylistLibraryEvent {

    public PlaylistLibraryCreatedEvent(UUID aggregateId, UserId user, Set<PlaylistId> playlists) {
        this(Instant.now(), aggregateId, user, playlists);
    }
}
