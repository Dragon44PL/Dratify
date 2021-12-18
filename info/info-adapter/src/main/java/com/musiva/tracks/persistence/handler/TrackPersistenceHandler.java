package com.musiva.tracks.persistence.handler;

import com.musiva.tracks.event.TrackCreatedEvent;
import com.musiva.tracks.event.TrackEvent;
import com.musiva.tracks.event.TrackListenedEvent;
import domain.events.DomainEventHandler;

public class TrackPersistenceHandler implements DomainEventHandler<TrackEvent> {

    private final TrackCreatedHandler trackCreatedHandler;
    private final TrackListenedHandler trackListenedHandler;

    public TrackPersistenceHandler(TrackCreatedHandler trackCreatedHandler, TrackListenedHandler trackListenedHandler) {
        this.trackCreatedHandler = trackCreatedHandler;
        this.trackListenedHandler = trackListenedHandler;
    }

    @Override
    public void handle(TrackEvent trackEvent) {
        if(trackEvent instanceof TrackCreatedEvent) {
            trackCreatedHandler.handle((TrackCreatedEvent) trackEvent);
        } else if(trackEvent instanceof TrackListenedEvent) {
            trackListenedHandler.handle((TrackListenedEvent) trackEvent);
        }
    }
}
