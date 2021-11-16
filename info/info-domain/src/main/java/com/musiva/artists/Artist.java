package com.musiva.artists;

import com.musiva.artists.event.ArtistEvent;
import com.musiva.artists.vo.ArtistType;
import com.musiva.domain.artists.event.ArtistCreatedEvent;
import domain.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Artist extends AggregateRoot<UUID, ArtistEvent> {

    private final UUID id;
    private final ArtistType artistType;
    private final String name;

    static Artist create(UUID id, String name, ArtistType artistType) {
        final Artist artist = new Artist(id, name, artistType, new ArrayList<>());
        final ArtistCreatedEvent artistCreatedEvent = new ArtistCreatedEvent(artist.id, artist.name, artist.artistType);
        artist.registerEvent(artistCreatedEvent);
        return artist;
    }

    static Artist restore(UUID id, String name, ArtistType artistType) {
        return new Artist(id, name, artistType, new ArrayList<>());
    }

    private Artist(UUID id, String name, ArtistType artistType, List<ArtistEvent> events) {
        super(events);
        this.id = id;
        this.name = name;
        this.artistType = artistType;
    }
}
