package com.musiva.playlists.playlist;

import com.musiva.playlists.playlist.event.*;
import com.musiva.playlists.playlist.vo.TrackId;
import com.musiva.playlists.playlist.vo.UserId;
import domain.AggregateRoot;

import java.util.*;

class Playlist extends AggregateRoot<UUID, PlaylistEvent> {

    private final UUID id;
    private final String name;
    private final UserId author;
    private final Set<UserId> collaborators;
    private final Set<TrackId> tracks;

    static Playlist create(UUID id, String name, UserId author, Set<UserId> collaborators, Set<TrackId> tracks) {
        final Playlist playlist = new Playlist(id, name, author, collaborators, tracks, new ArrayList<>());
        final PlaylistCreatedEvent playlistCreatedEvent = new PlaylistCreatedEvent(playlist.id, playlist.name, playlist.author, playlist.collaborators, playlist.tracks);
        playlist.registerEvent(playlistCreatedEvent);
        return playlist;
    }

    static Playlist restore(UUID id, String name, UserId author, Set<UserId> collaborators, Set<TrackId> tracks) {
        return new Playlist(id, name, author, collaborators, tracks, new ArrayList<>());
    }

    private Playlist(UUID id, String name, UserId author, Set<UserId> collaborators, Set<TrackId> tracks, List<PlaylistEvent> events) {
        super(events);
        this.id = id;
        this.name = name;
        this.author = author;
        this.collaborators = new HashSet<>(collaborators);
        this.tracks = new HashSet<>(tracks);
    }

    void addCollaborator(UserId collaborator) {
        if(!hasCollaborator(collaborator)) {
            processAddingCollaborator(collaborator);
        }
    }

    private void processAddingCollaborator(UserId collaborator) {
        collaborators.add(collaborator);
        final CollaboratorAddedEvent collaboratorAddedEvent = new CollaboratorAddedEvent(id, collaborator);
        this.registerEvent(collaboratorAddedEvent);
    }

    void removeCollaborator(UserId collaborator) {
        if(hasCollaborator(collaborator)) {
            processRemovingCollaborator(collaborator);
        }
    }

    private void processRemovingCollaborator(UserId collaborator) {
        collaborators.remove(collaborator);
        final CollaboratorRemovedEvent collaboratorRemovedEvent = new CollaboratorRemovedEvent(id, collaborator);
        this.registerEvent(collaboratorRemovedEvent);
    }

    boolean hasCollaborator(UserId collaborator) {
        return collaborators.contains(collaborator);
    }

    void addTrack(TrackId track) {
        if(!hasTrack(track)) {
            processAddingTrack(track);
        }
    }

    private void processAddingTrack(TrackId track) {
        tracks.add(track);
        final TrackAddedEvent trackAddedEvent = new TrackAddedEvent(id, track);
        this.registerEvent(trackAddedEvent);
    }

    void removeTrack(TrackId track) {
        if(hasTrack(track)) {
            processRemovingTrack(track);
        }
    }

    private void processRemovingTrack(TrackId track) {
        tracks.remove(track);
        final TrackRemovedEvent trackRemovedEvent = new TrackRemovedEvent(id, track);
        this.registerEvent(trackRemovedEvent);
    }

    boolean hasTrack(TrackId track) {
        return tracks.contains(track);
    }

}
