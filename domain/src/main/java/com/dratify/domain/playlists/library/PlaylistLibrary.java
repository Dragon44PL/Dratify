package com.dratify.domain.playlists.library;

import com.dratify.domain.playlists.library.event.PlaylistAddedEvent;
import com.dratify.domain.playlists.library.event.PlaylistLibraryCreatedEvent;
import com.dratify.domain.playlists.library.event.PlaylistLibraryEvent;
import com.dratify.domain.playlists.library.event.PlaylistRemovedEvent;
import com.dratify.domain.playlists.library.vo.PlaylistId;
import com.dratify.domain.playlists.library.vo.UserId;
import domain.AggregateRoot;

import java.util.*;

class PlaylistLibrary extends AggregateRoot<UUID, PlaylistLibraryEvent> {

    private final UUID id;
    private final UserId user;
    private final Set<PlaylistId> playlists;

    static PlaylistLibrary create(UUID id, UserId user, Set<PlaylistId> playlists) {
        final PlaylistLibrary playlistLibrary = new PlaylistLibrary(id, user, playlists, new ArrayList<>());
        playlistLibrary.registerEvent(new PlaylistLibraryCreatedEvent(playlistLibrary.id, playlistLibrary.user, playlistLibrary.playlists));
        return playlistLibrary;
    }

    static PlaylistLibrary restore(UUID id, UserId user, Set<PlaylistId> playlists) {
        return new PlaylistLibrary(id, user, playlists, new ArrayList<>());
    }

    private PlaylistLibrary(UUID id, UserId user, Set<PlaylistId> playlists, List<PlaylistLibraryEvent> events) {
        super(events);
        this.id = id;
        this.user = user;
        this.playlists = new HashSet<>(playlists);
    }

    void addPlaylist(PlaylistId playlist) {
        if(!hasPlaylist(playlist)) {
            processAddingPlaylist(playlist);
        }
    }

    private void processAddingPlaylist(PlaylistId playlist) {
        playlists.add(playlist);
        final PlaylistAddedEvent playlistAddedEvent = new PlaylistAddedEvent(id, playlist);
        this.registerEvent(playlistAddedEvent);
    }

    void removePlaylist(PlaylistId playlist) {
        if(hasPlaylist(playlist)) {
            processRemovingPlaylist(playlist);
        }
    }

    private void processRemovingPlaylist(PlaylistId playlist) {
        playlists.remove(playlist);
        final PlaylistRemovedEvent playlistAddedEvent = new PlaylistRemovedEvent(id, playlist);
        this.registerEvent(playlistAddedEvent);
    }

    boolean hasPlaylist(PlaylistId playlist) {
        return playlists.contains(playlist);
    }

}
