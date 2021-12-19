package com.musiva.tracks.library.persistence.handler;

import com.musiva.tracks.library.event.TrackLibraryCreatedEvent;
import com.musiva.tracks.library.persistence.JpaTrackLibraryRepository;
import com.musiva.tracks.library.persistence.TrackLibraryEntity;

import com.musiva.tracks.library.vo.TrackId;
import domain.events.DomainEventHandler;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TrackLibraryCreatedHandler implements DomainEventHandler<TrackLibraryCreatedEvent> {

    private final JpaTrackLibraryRepository trackLibraryRepository;

    public TrackLibraryCreatedHandler(JpaTrackLibraryRepository trackLibraryRepository) {
        this.trackLibraryRepository = trackLibraryRepository;
    }

    @Override
    public void handle(TrackLibraryCreatedEvent trackLibraryCreatedEvent) {
        final Set<UUID> tracks = trackLibraryCreatedEvent.tracks().stream().map(TrackId::id).collect(Collectors.toSet());

        final TrackLibraryEntity trackLibraryEntity = new TrackLibraryEntity();
        trackLibraryEntity.setId(trackLibraryCreatedEvent.aggregateId());
        trackLibraryEntity.setUserId(trackLibraryCreatedEvent.user().id());
        trackLibraryEntity.setTracks(tracks);

        trackLibraryRepository.saveAndFlush(trackLibraryEntity);
    }
}
