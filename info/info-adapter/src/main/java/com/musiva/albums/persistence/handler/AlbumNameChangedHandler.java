package com.musiva.albums.persistence.handler;

import com.musiva.albums.event.AlbumNameChangedEvent;
import com.musiva.albums.persistence.AlbumEntity;
import com.musiva.albums.persistence.JpaAlbumRepository;

import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlbumNameChangedHandler implements DomainEventHandler<AlbumNameChangedEvent> {

    private final JpaAlbumRepository jpaAlbumRepository;

    public AlbumNameChangedHandler(JpaAlbumRepository jpaAlbumRepository) {
        this.jpaAlbumRepository = jpaAlbumRepository;
    }

    @Override
    public void handle(AlbumNameChangedEvent albumNameChangedEvent) {
        final Optional<AlbumEntity> albumEntity = jpaAlbumRepository.findById(albumNameChangedEvent.aggregateId());

        albumEntity.ifPresent(found -> {
            found.setName(albumNameChangedEvent.name());
            jpaAlbumRepository.saveAndFlush(found);
        });
    }
}
