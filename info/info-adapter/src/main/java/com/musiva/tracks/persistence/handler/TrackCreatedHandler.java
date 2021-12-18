package com.musiva.tracks.persistence.handler;

import com.musiva.tracks.event.TrackCreatedEvent;
import com.musiva.tracks.persistence.TrackDataEntity;
import com.musiva.tracks.persistence.JpaTrackRepository;
import com.musiva.tracks.persistence.TrackCounterEntity;
import com.musiva.tracks.persistence.TrackEntity;
import domain.events.DomainEventHandler;

public class TrackCreatedHandler implements DomainEventHandler<TrackCreatedEvent> {

    private final JpaTrackRepository jpaTrackRepository;

    public TrackCreatedHandler(JpaTrackRepository jpaTrackRepository) {
        this.jpaTrackRepository = jpaTrackRepository;
    }

    @Override
    public void handle(TrackCreatedEvent trackCreatedEvent) {
        final TrackCounterEntity trackCounterEntity = new TrackCounterEntity();
        trackCounterEntity.setCounter(trackCreatedEvent.listeningCounter().count());

        final TrackDataEntity trackDataEntity = new TrackDataEntity();
        trackDataEntity.setPath(trackDataEntity.getPath());
        trackDataEntity.setName(trackCreatedEvent.name());
        trackDataEntity.setExtension(trackCreatedEvent.trackDataPath().extension());

        final TrackEntity trackEntity = new TrackEntity();
        trackEntity.setId(trackCreatedEvent.aggregateId());
        trackEntity.setName(trackCreatedEvent.name());
        trackEntity.setTrackCounterEntity(trackCounterEntity);
        trackEntity.setTrackDataEntity(trackDataEntity);

        jpaTrackRepository.save(trackEntity);
    }
}
