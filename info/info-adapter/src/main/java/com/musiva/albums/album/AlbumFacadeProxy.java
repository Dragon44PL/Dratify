package com.musiva.albums.album;

import com.musiva.albums.album.commands.CreateAlbumCommand;
import com.musiva.albums.album.commands.InsertTrackCommand;
import com.musiva.albums.album.event.AlbumEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumFacadeProxy extends AlbumFacade {

    public AlbumFacadeProxy(AlbumRepository albumRepository) {
        super(albumRepository);
    }

    @Override
    @Transactional
    public List<AlbumEvent> createAlbum(CreateAlbumCommand createAlbumCommand) {
        return super.createAlbum(createAlbumCommand);
    }

    @Override
    @Transactional
    public List<AlbumEvent> insertTrack(InsertTrackCommand insertTrackCommand) {
        return super.insertTrack(insertTrackCommand);
    }
}
