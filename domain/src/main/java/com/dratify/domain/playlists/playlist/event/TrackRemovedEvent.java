package com.dratify.domain.playlists.playlist.event;

import com.dratify.domain.playlists.playlist.vo.TrackId;
import java.time.Instant;
import java.util.UUID;

public record TrackRemovedEvent(Instant occurredOn, UUID aggregateId, TrackId track) implements PlaylistEvent {

    public TrackRemovedEvent(UUID playlistId, TrackId track) {
        this(Instant.now(), playlistId, track);
    }
}
