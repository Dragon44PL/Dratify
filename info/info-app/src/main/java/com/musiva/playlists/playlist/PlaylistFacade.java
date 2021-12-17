package com.musiva.playlists.playlist;

import com.musiva.playlists.playlist.commands.CreatePlaylistCommand;
import com.musiva.playlists.playlist.commands.InsertCollaborateCommand;
import com.musiva.playlists.playlist.commands.InsertTrackCommand;
import com.musiva.playlists.playlist.event.PlaylistEvent;
import com.musiva.playlists.playlist.exception.PlaylistAlreadyDefinedException;

import java.util.*;

public class PlaylistFacade {

    private final PlaylistRepository playlistRepository;

    public PlaylistFacade(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public List<PlaylistEvent> createPlaylist(CreatePlaylistCommand createPlaylistCommand) {
        final Optional<Playlist> playlist = playlistRepository.findByName(createPlaylistCommand.name());

        if(playlist.isPresent()) {
            throw new PlaylistAlreadyDefinedException("Playlist with name : '" + createPlaylistCommand.name() + "' already defined");
        }

        final UUID randomId = UUID.randomUUID();
        final Playlist created = Playlist.create(randomId, createPlaylistCommand.name(), createPlaylistCommand.author(), new HashSet<>(), new HashSet<>());
        playlistRepository.save(created);
        return created.events();
    }

    public List<PlaylistEvent> insertCollaborator(InsertCollaborateCommand insertCollaborateCommand) {
        final Optional<Playlist> playlist = playlistRepository.findById(insertCollaborateCommand.id());
        return playlist.map(value -> processInsertingCollaborate(value, insertCollaborateCommand)).orElseGet(ArrayList::new);
    }

    private List<PlaylistEvent> processInsertingCollaborate(Playlist playlist, InsertCollaborateCommand insertCollaborateCommand) {
        playlist.addCollaborator(insertCollaborateCommand.collaborate());
        playlistRepository.save(playlist);
        return playlist.events();
    }

    public List<PlaylistEvent> insertTrack(InsertTrackCommand insertTrackCommand) {
        final Optional<Playlist> playlist = playlistRepository.findById(insertTrackCommand.id());
        return playlist.map(value -> processInsertingTrack(value, insertTrackCommand)).orElseGet(ArrayList::new);
    }

    private List<PlaylistEvent> processInsertingTrack(Playlist playlist, InsertTrackCommand insertTrackCommand) {
        playlist.addTrack(insertTrackCommand.track());
        playlistRepository.save(playlist);
        return playlist.events();
    }

}
