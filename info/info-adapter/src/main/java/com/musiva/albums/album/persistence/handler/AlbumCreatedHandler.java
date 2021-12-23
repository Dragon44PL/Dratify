package com.musiva.albums.album.persistence.handler;

import com.musiva.albums.album.event.AlbumCreatedEvent;
import com.musiva.albums.album.persistence.AlbumEntity;
import com.musiva.albums.album.persistence.JpaAlbumRepository;
import com.musiva.albums.album.vo.TrackId;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AlbumCreatedHandler implements DomainEventHandler<AlbumCreatedEvent> {

    private final JpaAlbumRepository jpaAlbumRepository;

    public AlbumCreatedHandler(JpaAlbumRepository jpaAlbumRepository) {
        this.jpaAlbumRepository = jpaAlbumRepository;
    }

    @Override
    public void handle(AlbumCreatedEvent albumCreatedEvent) {
        final AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setId(albumCreatedEvent.aggregateId());
        albumEntity.setArtistId(albumCreatedEvent.artist().id());
        albumEntity.setName(albumEntity.getName());

        final Set<UUID> tracks = albumCreatedEvent.tracks().stream().map(TrackId::id).collect(Collectors.toSet());
        albumEntity.setTracks(tracks);

        jpaAlbumRepository.saveAndFlush(albumEntity);
    }
}
