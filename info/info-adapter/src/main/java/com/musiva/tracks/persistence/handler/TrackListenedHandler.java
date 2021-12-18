package com.musiva.tracks.persistence.handler;

import com.musiva.tracks.event.TrackListenedEvent;
import com.musiva.tracks.persistence.JpaTrackRepository;
import com.musiva.tracks.persistence.TrackCounterEntity;
import com.musiva.tracks.persistence.TrackEntity;
import domain.events.DomainEventHandler;

public class TrackListenedHandler implements DomainEventHandler<TrackListenedEvent> {

    private final JpaTrackRepository jpaTrackRepository;

    public TrackListenedHandler(JpaTrackRepository jpaTrackRepository) {
        this.jpaTrackRepository = jpaTrackRepository;
    }

    @Override
    public void handle(TrackListenedEvent trackListenedEvent) {
        jpaTrackRepository.findById(trackListenedEvent.aggregateId()).ifPresent(entity -> {
            processHandlingEvent(entity, trackListenedEvent);
        });
    }

    private void processHandlingEvent(TrackEntity trackEntity, TrackListenedEvent trackListenedEvent) {
        final TrackCounterEntity trackCounterEntity = trackEntity.getTrackCounterEntity();
        trackCounterEntity.setCounter(trackListenedEvent.listeningCounter().count());
        jpaTrackRepository.save(trackEntity);
    }
}
