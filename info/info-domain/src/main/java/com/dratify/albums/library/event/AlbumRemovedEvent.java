package com.dratify.albums.library.event;

import com.dratify.albums.library.vo.AlbumId;
import java.time.Instant;
import java.util.UUID;

public record AlbumRemovedEvent(Instant occurredOn, UUID aggregateId, AlbumId album) implements AlbumLibraryEvent {

    public AlbumRemovedEvent(UUID aggregateId, AlbumId album) {
        this(Instant.now(), aggregateId, album);
    }
}