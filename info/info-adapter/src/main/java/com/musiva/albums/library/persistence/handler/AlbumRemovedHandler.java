package com.musiva.albums.library.persistence.handler;

import com.musiva.albums.library.event.AlbumRemovedEvent;
import com.musiva.albums.library.persistence.AlbumLibraryEntity;
import com.musiva.albums.library.persistence.JpaAlbumLibraryEntityRepository;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class AlbumRemovedHandler implements DomainEventHandler<AlbumRemovedEvent> {

    private final JpaAlbumLibraryEntityRepository albumLibraryEntityRepository;

    public AlbumRemovedHandler(JpaAlbumLibraryEntityRepository albumLibraryEntityRepository) {
        this.albumLibraryEntityRepository = albumLibraryEntityRepository;
    }

    @Override
    public void handle(AlbumRemovedEvent albumRemovedEvent) {
        final Optional<AlbumLibraryEntity> albumLibraryEntity = albumLibraryEntityRepository.findById(albumRemovedEvent.aggregateId());

        albumLibraryEntity.ifPresent(found -> {
            final Set<UUID> albums = found.getAlbums();
            albums.remove(albumRemovedEvent.album().id());
            albumLibraryEntityRepository.saveAndFlush(found);
        });
    }
}
