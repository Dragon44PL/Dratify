package com.dratify.domain.playlists.library.event;

import com.dratify.domain.playlists.library.vo.PlaylistId;
import java.time.Instant;
import java.util.UUID;

public record PlaylistRemovedEvent(Instant occurredOn, UUID aggregateId, PlaylistId playlist) implements PlaylistLibraryEvent {

    public PlaylistRemovedEvent(UUID aggregateId, PlaylistId playlist) {
        this(Instant.now(), aggregateId, playlist);
    }
}
