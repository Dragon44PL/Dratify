package com.musiva.artists.persistence.handler;

import com.musiva.artists.event.ArtistCreatedEvent;
import com.musiva.artists.event.ArtistEvent;
import domain.events.DomainEventHandler;

public class ArtistPersistenceHandler implements DomainEventHandler<ArtistEvent> {

    private final ArtistCreatedHandler artistCreatedHandler;

    public ArtistPersistenceHandler(ArtistCreatedHandler artistCreatedHandler) {
        this.artistCreatedHandler = artistCreatedHandler;
    }

    @Override
    public void handle(ArtistEvent artistEvent) {
        if(artistEvent instanceof ArtistCreatedEvent) {
            artistCreatedHandler.handle((ArtistCreatedEvent) artistEvent);
        }
    }
}
