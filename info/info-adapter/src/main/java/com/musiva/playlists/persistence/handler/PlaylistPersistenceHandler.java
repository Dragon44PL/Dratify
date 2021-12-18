package com.musiva.playlists.persistence.handler;

import com.musiva.playlists.event.CollaboratorAddedEvent;
import com.musiva.playlists.event.PlaylistCreatedEvent;
import com.musiva.playlists.event.PlaylistEvent;
import com.musiva.playlists.event.TrackAddedEvent;
import domain.events.DomainEventHandler;
import org.springframework.stereotype.Component;

@Component
public class PlaylistPersistenceHandler implements DomainEventHandler<PlaylistEvent> {

    private final CollaboratorAddedHandler collaboratorAddedHandler;
    private final PlaylistCreatedHandler playlistCreatedHandler;
    private final TrackAddedHandler trackAddedHandler;

    public PlaylistPersistenceHandler(CollaboratorAddedHandler collaboratorAddedHandler, PlaylistCreatedHandler playlistCreatedHandler, TrackAddedHandler trackAddedHandler) {
        this.collaboratorAddedHandler = collaboratorAddedHandler;
        this.playlistCreatedHandler = playlistCreatedHandler;
        this.trackAddedHandler = trackAddedHandler;
    }

    @Override
    public void handle(PlaylistEvent playlistEvent) {
        if(playlistEvent instanceof PlaylistCreatedEvent) {
            playlistCreatedHandler.handle((PlaylistCreatedEvent) playlistEvent);
        } else if(playlistEvent instanceof CollaboratorAddedEvent) {
            collaboratorAddedHandler.handle((CollaboratorAddedEvent) playlistEvent);
        } else if(playlistEvent instanceof TrackAddedEvent) {
            trackAddedHandler.handle((TrackAddedEvent) playlistEvent);
        }
    }
}
