package com.musiva.albums.library.persistence.handler;

import com.musiva.albums.library.event.AlbumLibraryCreatedEvent;
import com.musiva.albums.library.persistence.AlbumLibraryEntity;
import com.musiva.albums.library.persistence.JpaAlbumLibraryEntityRepository;
import com.musiva.albums.library.vo.AlbumId;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AlbumLibraryCreatedHandler implements DomainEventHandler<AlbumLibraryCreatedEvent> {

    private final JpaAlbumLibraryEntityRepository albumLibraryEntityRepository;

    public AlbumLibraryCreatedHandler(JpaAlbumLibraryEntityRepository albumLibraryEntityRepository) {
        this.albumLibraryEntityRepository = albumLibraryEntityRepository;
    }

    @Override
    public void handle(AlbumLibraryCreatedEvent albumLibraryCreatedEvent) {
        final AlbumLibraryEntity albumLibraryEntity = new AlbumLibraryEntity();
        albumLibraryEntity.setId(albumLibraryCreatedEvent.aggregateId());
        albumLibraryEntity.setUserId(albumLibraryCreatedEvent.user().id());
        albumLibraryEntity.setAlbums(albumLibraryCreatedEvent.albums().stream().map(AlbumId::id).collect(Collectors.toSet()));

        albumLibraryEntityRepository.saveAndFlush(albumLibraryEntity);
    }
}
