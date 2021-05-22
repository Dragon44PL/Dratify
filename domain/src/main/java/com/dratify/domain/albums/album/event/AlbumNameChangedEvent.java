package com.dratify.domain.albums.album.event;

import java.time.Instant;
import java.util.UUID;

public record AlbumNameChangedEvent(Instant occurredOn, UUID aggregateId, String name) implements AlbumEvent {

    public AlbumNameChangedEvent(UUID albumId, String name) {
        this(Instant.now(), albumId, name);
    }
}
