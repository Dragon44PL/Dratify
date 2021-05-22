package com.dratify.domain.albums.library.event;

import com.dratify.domain.albums.library.vo.AlbumId;
import java.time.Instant;
import java.util.UUID;

public record AlbumAddedEvent(Instant occurredOn, UUID aggregateId, AlbumId album) implements AlbumLibraryEvent {

    public AlbumAddedEvent(UUID aggregateId, AlbumId album) {
        this(Instant.now(), aggregateId, album);
    }
}
