package com.dratify.domain.playlists.library.event;

import com.dratify.domain.playlists.library.vo.PlaylistId;
import java.time.Instant;
import java.util.UUID;

public record PlaylistAddedEvent(Instant occurredOn, UUID aggregateId, PlaylistId playlist) implements PlaylistLibraryEvent {

    public PlaylistAddedEvent(UUID aggregateId, PlaylistId playlist) {
        this(Instant.now(), aggregateId, playlist);
    }
}
