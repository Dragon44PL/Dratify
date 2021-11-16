package com.musiva.playlists.library;

import com.musiva.playlists.library.commands.CreatePlaylistLibraryCommand;
import com.musiva.playlists.library.commands.InsertPlaylistCommand;
import com.musiva.playlists.library.event.PlaylistLibraryEvent;

import java.util.*;

public class PlaylistLibraryFacade {

    private final PlaylistLibraryRepository playlistLibraryRepository;

    public PlaylistLibraryFacade(PlaylistLibraryRepository playlistLibraryRepository) {
        this.playlistLibraryRepository = playlistLibraryRepository;
    }

    public List<PlaylistLibraryEvent> createPlaylistLibrary(CreatePlaylistLibraryCommand createPlaylistLibraryCommand) {
        final UUID randomId = UUID.randomUUID();
        final PlaylistLibrary playlistLibrary = PlaylistLibrary.create(randomId, createPlaylistLibraryCommand.userId(), new HashSet<>());
        playlistLibraryRepository.save(playlistLibrary);
        return playlistLibrary.events();
    }

    public List<PlaylistLibraryEvent> insertPlaylist(InsertPlaylistCommand insertPlaylistCommand) {
        final Optional<PlaylistLibrary> playlistLibrary = playlistLibraryRepository.findById(insertPlaylistCommand.id());
        return playlistLibrary.map(library -> processInsertingPlaylist(library, insertPlaylistCommand)).orElseGet(ArrayList::new);
    }

    private List<PlaylistLibraryEvent> processInsertingPlaylist(PlaylistLibrary playlistLibrary, InsertPlaylistCommand insertPlaylistCommand) {
        playlistLibrary.addPlaylist(insertPlaylistCommand.playlistId());
        playlistLibraryRepository.save(playlistLibrary);
        return playlistLibrary.events();
    }

}
