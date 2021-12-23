package com.musiva.albums.album.event;

import com.musiva.albums.album.vo.ArtistId;
import com.musiva.albums.album.vo.TrackId;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record AlbumCreatedEvent(Instant occurredOn, UUID aggregateId, String name, Set<TrackId> tracks, ArtistId artist) implements AlbumEvent {

    public AlbumCreatedEvent(UUID albumId, String name, Set<TrackId> tracks, ArtistId artist) {
        this(Instant.now(), albumId, name, tracks, artist);
    }
}
