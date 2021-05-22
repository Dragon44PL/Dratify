package com.dratify.domain.albums.library.event;

import com.dratify.domain.albums.library.vo.AlbumId;
import com.dratify.domain.albums.library.vo.UserId;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record AlbumLibraryCreatedEvent(Instant occurredOn, UUID aggregateId, UserId user, Set<AlbumId> albums) implements AlbumLibraryEvent {

    public AlbumLibraryCreatedEvent(UUID aggregateId, UserId user, Set<AlbumId> albums) {
        this(Instant.now(), aggregateId, user, albums);
    }
}
