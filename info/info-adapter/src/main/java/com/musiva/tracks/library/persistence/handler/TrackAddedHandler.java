package com.musiva.tracks.library.persistence.handler;

import com.musiva.tracks.library.event.TrackAddedEvent;
import com.musiva.tracks.library.persistence.JpaTrackLibraryRepository;
import com.musiva.tracks.library.persistence.TrackLibraryEntity;
import domain.events.DomainEventHandler;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class TrackAddedHandler implements DomainEventHandler<TrackAddedEvent> {

    private final JpaTrackLibraryRepository jpaTrackLibraryRepository;

    public TrackAddedHandler(JpaTrackLibraryRepository jpaTrackLibraryRepository) {
        this.jpaTrackLibraryRepository = jpaTrackLibraryRepository;
    }

    @Override
    public void handle(TrackAddedEvent trackAddedEvent) {
        final Optional<TrackLibraryEntity> trackLibraryEntity = jpaTrackLibraryRepository.findById(trackAddedEvent.aggregateId());

        trackLibraryEntity.ifPresent(found -> {
            final Set<UUID> tracks = found.getTracks();
            tracks.add(trackAddedEvent.track().id());
            jpaTrackLibraryRepository.saveAndFlush(found);
        });
    }
}
