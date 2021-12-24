package com.musiva.albums.library.persistence.handler;

import com.musiva.albums.library.event.AlbumAddedEvent;
import com.musiva.albums.library.persistence.AlbumLibraryEntity;
import com.musiva.albums.library.persistence.JpaAlbumLibraryEntityRepository;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class AlbumAddedHandler implements DomainEventHandler<AlbumAddedEvent> {

    private final JpaAlbumLibraryEntityRepository albumLibraryEntityRepository;

    public AlbumAddedHandler(JpaAlbumLibraryEntityRepository albumLibraryEntityRepository) {
        this.albumLibraryEntityRepository = albumLibraryEntityRepository;
    }

    @Override
    public void handle(AlbumAddedEvent albumAddedEvent) {
        final Optional<AlbumLibraryEntity> albumLibraryEntity = albumLibraryEntityRepository.findById(albumAddedEvent.aggregateId());

        albumLibraryEntity.ifPresent(found -> {
            final Set<UUID> albums = found.getAlbums();
            albums.add(albumAddedEvent.album().id());
            albumLibraryEntityRepository.saveAndFlush(found);
        });
    }
}
