package com.musiva.playlists.playlist.persistence.handler;

import com.musiva.playlists.playlist.event.TrackAddedEvent;
import com.musiva.playlists.playlist.persistence.JpaPlaylistRepository;
import com.musiva.playlists.playlist.persistence.PlaylistEntity;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class TrackAddedHandler implements DomainEventHandler<TrackAddedEvent> {

    private final JpaPlaylistRepository jpaPlaylistRepository;

    public TrackAddedHandler(JpaPlaylistRepository jpaPlaylistRepository) {
        this.jpaPlaylistRepository = jpaPlaylistRepository;
    }

    @Override
    public void handle(TrackAddedEvent trackAddedEvent) {
        final Optional<PlaylistEntity> playlist = jpaPlaylistRepository.findById(trackAddedEvent.aggregateId());

        playlist.ifPresent(found -> {
            final Set<UUID> tracks = found.getTracks();
            tracks.add(trackAddedEvent.track().id());
            jpaPlaylistRepository.saveAndFlush(found);
        });
    }
}
