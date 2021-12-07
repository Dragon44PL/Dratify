package com.musiva.albums.album.persistence.handler;

import com.musiva.albums.album.event.TrackRemovedEvent;
import com.musiva.albums.album.persistence.AlbumEntity;
import com.musiva.albums.album.persistence.JpaAlbumRepository;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class TrackRemovedHandler implements DomainEventHandler<TrackRemovedEvent> {

    private final JpaAlbumRepository jpaAlbumRepository;

    public TrackRemovedHandler(JpaAlbumRepository jpaAlbumRepository) {
        this.jpaAlbumRepository = jpaAlbumRepository;
    }

    @Override
    public void handle(TrackRemovedEvent trackRemovedEvent) {
        final Optional<AlbumEntity> albumEntity = jpaAlbumRepository.findById(trackRemovedEvent.aggregateId());

        albumEntity.ifPresent(found -> {
            final Set<UUID> tracks = found.getTracks();
            tracks.remove(trackRemovedEvent.track().id());
            jpaAlbumRepository.saveAndFlush(found);
        });
    }
}
