package com.musiva.albums.persistence.handler;

import com.musiva.albums.album.event.*;
import com.musiva.albums.event.*;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

@Component
public class AlbumPersistenceHandler implements DomainEventHandler<AlbumEvent> {

    private final AlbumCreatedHandler albumCreatedHandler;
    private final AlbumNameChangedHandler albumNameChangedHandler;
    private final TrackAddedHandler trackAddedHandler;
    private final TrackRemovedHandler trackRemovedHandler;

    public AlbumPersistenceHandler(AlbumCreatedHandler albumCreatedHandler, AlbumNameChangedHandler albumNameChangedHandler, TrackAddedHandler trackAddedHandler, TrackRemovedHandler trackRemovedHandler) {
        this.albumCreatedHandler = albumCreatedHandler;
        this.albumNameChangedHandler = albumNameChangedHandler;
        this.trackAddedHandler = trackAddedHandler;
        this.trackRemovedHandler = trackRemovedHandler;
    }

    @Override
    public void handle(AlbumEvent albumEvent) {
        if(albumEvent instanceof AlbumCreatedEvent) {
            albumCreatedHandler.handle((AlbumCreatedEvent) albumEvent);
        } else if(albumEvent instanceof AlbumNameChangedEvent) {
            albumNameChangedHandler.handle((AlbumNameChangedEvent) albumEvent);
        } else if(albumEvent instanceof TrackAddedEvent) {
            trackAddedHandler.handle((TrackAddedEvent) albumEvent);
        } else if(albumEvent instanceof TrackRemovedEvent) {
            trackRemovedHandler.handle((TrackRemovedEvent) albumEvent);
        }
    }
}
