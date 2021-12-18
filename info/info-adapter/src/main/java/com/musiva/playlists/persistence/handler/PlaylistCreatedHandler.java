package com.musiva.playlists.persistence.handler;

import com.musiva.playlists.persistence.PlaylistEntity;
import com.musiva.playlists.event.PlaylistCreatedEvent;
import com.musiva.playlists.persistence.JpaPlaylistRepository;
import com.musiva.playlists.vo.TrackId;
import com.musiva.playlists.vo.UserId;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PlaylistCreatedHandler implements DomainEventHandler<PlaylistCreatedEvent> {

    private final JpaPlaylistRepository jpaPlaylistRepository;

    public PlaylistCreatedHandler(JpaPlaylistRepository jpaPlaylistRepository) {
        this.jpaPlaylistRepository = jpaPlaylistRepository;
    }

    @Override
    public void handle(PlaylistCreatedEvent playlistCreatedEvent) {
        final Set<UUID> collaborators = playlistCreatedEvent.collaborators().stream().map(UserId::id).collect(Collectors.toSet());
        final Set<UUID> tracks = playlistCreatedEvent.tracks().stream().map(TrackId::id).collect(Collectors.toSet());

        final PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(playlistCreatedEvent.aggregateId());
        playlistEntity.setName(playlistCreatedEvent.name());
        playlistEntity.setAuthorId(playlistCreatedEvent.author().id());
        playlistEntity.setCollaborators(collaborators);
        playlistEntity.setTracks(tracks);

        jpaPlaylistRepository.saveAndFlush(playlistEntity);
    }
}
