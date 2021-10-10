package com.dratify.artists.event;

public record ArtistCreatedEvent(Instant occurredOn, UUID aggregateId, String name, ArtistType artistType) implements ArtistEvent {

    public ArtistCreatedEvent(UUID artistId, String name, ArtistType artistType) {
        this(Instant.now(), artistId, name, artistType);
    }
}
