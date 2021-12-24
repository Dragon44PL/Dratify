package com.musiva.albums.library.persistence.handler;

import com.musiva.albums.library.event.AlbumAddedEvent;
import com.musiva.albums.library.event.AlbumLibraryCreatedEvent;
import com.musiva.albums.library.event.AlbumLibraryEvent;

import com.musiva.albums.library.event.AlbumRemovedEvent;
import domain.events.DomainEventHandler;

import org.springframework.stereotype.Component;

@Component
public class AlbumLibraryPersistenceHandler implements DomainEventHandler<AlbumLibraryEvent> {

    private final AlbumLibraryCreatedHandler albumLibraryCreatedHandler;
    private final AlbumAddedHandler albumAddedHandler;
    private final AlbumRemovedHandler albumRemovedHandler;

    public AlbumLibraryPersistenceHandler(AlbumLibraryCreatedHandler albumLibraryCreatedHandler, AlbumAddedHandler albumAddedHandler, AlbumRemovedHandler albumRemovedHandler) {
        this.albumLibraryCreatedHandler = albumLibraryCreatedHandler;
        this.albumAddedHandler = albumAddedHandler;
        this.albumRemovedHandler = albumRemovedHandler;
    }

    @Override
    public void handle(AlbumLibraryEvent albumLibraryEvent) {
        if(albumLibraryEvent instanceof AlbumLibraryCreatedEvent) {
            albumLibraryCreatedHandler.handle((AlbumLibraryCreatedEvent) albumLibraryEvent);
        } else if(albumLibraryEvent instanceof AlbumAddedEvent) {
            albumAddedHandler.handle((AlbumAddedEvent) albumLibraryEvent);
        } else if(albumLibraryEvent instanceof AlbumRemovedEvent) {
            albumRemovedHandler.handle((AlbumRemovedEvent) albumLibraryEvent);
        }
    }
}
