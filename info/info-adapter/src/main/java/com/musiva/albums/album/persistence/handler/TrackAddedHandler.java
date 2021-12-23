package com.musiva.albums.album.persistence.handler;

import com.musiva.albums.album.event.TrackAddedEvent;
import com.musiva.albums.album.persistence.AlbumEntity;
import com.musiva.albums.album.persistence.JpaAlbumRepository;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class TrackAddedHandler implements DomainEventHandler<TrackAddedEvent> {

    private final JpaAlbumRepository jpaAlbumRepository;

    public TrackAddedHandler(JpaAlbumRepository jpaAlbumRepository) {
        this.jpaAlbumRepository = jpaAlbumRepository;
    }

    @Override
    public void handle(TrackAddedEvent trackAddedEvent) {
        final Optional<AlbumEntity> albumEntity = jpaAlbumRepository.findById(trackAddedEvent.aggregateId());

        albumEntity.ifPresent(found -> {
            final Set<UUID> tracks = found.getTracks();
            tracks.add(trackAddedEvent.track().id());
            jpaAlbumRepository.saveAndFlush(found);
        });
    }
}
