package com.dratify.domain.playlists;

import com.dratify.domain.playlists.event.*;
import com.dratify.domain.playlists.vo.TrackId;
import com.dratify.domain.playlists.vo.UserId;
import domain.AggregateRoot;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
        this.collaborators = collaborators;
        this.tracks = tracks;
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

    void addTrack(TrackId song) {
        if(!hasSong(song)) {
            processAddingTrack(song);
        }
    }

    private void processAddingTrack(TrackId song) {
        tracks.add(song);
        final TrackAddedEvent trackAddedEvent = new TrackAddedEvent(id, song);
        this.registerEvent(trackAddedEvent);
    }

    void removeTrack(TrackId song) {
        if(hasSong(song)) {
            processRemovingTrack(song);
        }
    }

    private void processRemovingTrack(TrackId song) {
        tracks.remove(song);
        final TrackRemovedEvent trackRemovedEvent = new TrackRemovedEvent(id, song);
        this.registerEvent(trackRemovedEvent);
    }

    boolean hasSong(TrackId song) {
        return tracks.contains(song);
    }

}
