package com.musiva.playlists.event;

import com.musiva.playlists.vo.TrackId;

import java.time.Instant;
import java.util.UUID;

public record TrackAddedEvent(Instant occurredOn, UUID aggregateId, TrackId track) implements PlaylistEvent {

    public TrackAddedEvent(UUID playlistId, TrackId track) {
        this(Instant.now(), playlistId, track);
    }
}
