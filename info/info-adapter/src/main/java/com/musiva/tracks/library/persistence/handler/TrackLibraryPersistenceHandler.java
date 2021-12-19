package com.musiva.tracks.library.persistence.handler;

import com.musiva.tracks.library.event.TrackAddedEvent;
import com.musiva.tracks.library.event.TrackLibraryCreatedEvent;
import com.musiva.tracks.library.event.TrackLibraryEvent;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

@Component
public class TrackLibraryPersistenceHandler implements DomainEventHandler<TrackLibraryEvent> {

    private final TrackLibraryCreatedHandler trackLibraryCreatedHandler;
    private final TrackAddedHandler trackAddedHandler;

    public TrackLibraryPersistenceHandler(TrackLibraryCreatedHandler trackLibraryCreatedHandler, TrackAddedHandler trackAddedHandler) {
        this.trackLibraryCreatedHandler = trackLibraryCreatedHandler;
        this.trackAddedHandler = trackAddedHandler;
    }

    @Override
    public void handle(TrackLibraryEvent trackLibraryEvent) {
        if(trackLibraryEvent instanceof TrackLibraryCreatedEvent) {
            trackLibraryCreatedHandler.handle((TrackLibraryCreatedEvent) trackLibraryEvent);
        } else if(trackLibraryEvent instanceof TrackAddedEvent) {
            trackAddedHandler.handle((TrackAddedEvent) trackLibraryEvent);
        }
    }
}
