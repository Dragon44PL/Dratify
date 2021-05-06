package com.dratify.domain.playlists;

import com.dratify.domain.playlists.event.PlaylistCreatedEvent;
import com.dratify.domain.playlists.event.PlaylistEvent;
import com.dratify.domain.playlists.event.TrackAddedEvent;
import com.dratify.domain.playlists.event.TrackRemovedEvent;
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
    private final UserId user;
    private final Set<TrackId> tracks;

    static Playlist create(UUID id, String name, UserId user, Set<TrackId> tracks) {
        final Playlist playlist = new Playlist(id, name, user, tracks, new ArrayList<>());
        final PlaylistCreatedEvent playlistCreatedEvent = new PlaylistCreatedEvent(playlist.id, playlist.name, playlist.user, playlist.tracks);
        playlist.registerEvent(playlistCreatedEvent);
        return playlist;
    }

    static Playlist restore(UUID id, String name, UserId user, Set<TrackId> tracks) {
        return new Playlist(id, name, user, tracks, new ArrayList<>());
    }

    private Playlist(UUID id, String name, UserId user, Set<TrackId> tracks, List<PlaylistEvent> events) {
        super(events);
        this.id = id;
        this.name = name;
        this.user = user;
        this.tracks = tracks;
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
