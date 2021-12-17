package com.musiva.playlists.playlist;

import com.musiva.playlists.playlist.commands.CreatePlaylistCommand;
import com.musiva.playlists.playlist.commands.InsertCollaborateCommand;
import com.musiva.playlists.playlist.commands.InsertTrackCommand;
import com.musiva.playlists.playlist.event.PlaylistEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaylistFacadeProxy extends PlaylistFacade {

    public PlaylistFacadeProxy(PlaylistRepository playlistRepository) {
        super(playlistRepository);
    }

    @Override
    @Transactional
    public List<PlaylistEvent> createPlaylist(CreatePlaylistCommand createPlaylistCommand) {
        return super.createPlaylist(createPlaylistCommand);
    }

    @Override
    @Transactional
    public List<PlaylistEvent> insertCollaborator(InsertCollaborateCommand insertCollaborateCommand) {
        return super.insertCollaborator(insertCollaborateCommand);
    }

    @Override
    @Transactional
    public List<PlaylistEvent> insertTrack(InsertTrackCommand insertTrackCommand) {
        return super.insertTrack(insertTrackCommand);
    }
}
