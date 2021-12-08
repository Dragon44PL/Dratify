package com.musiva.albums.album;

import com.musiva.albums.album.commands.CreateAlbumCommand;
import com.musiva.albums.album.commands.InsertTrackCommand;
import com.musiva.albums.album.event.AlbumEvent;
import com.musiva.albums.album.exception.AlbumAlreadyCreatedException;

import java.util.*;

public class AlbumFacade {

    private final AlbumRepository albumRepository;

    public AlbumFacade(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<AlbumEvent> createAlbum(CreateAlbumCommand createAlbumCommand) {
        Optional<Album> found = albumRepository.findByNameAndArtist(createAlbumCommand.name(), createAlbumCommand.artistId());

        found.ifPresent(f -> {
            throw new AlbumAlreadyCreatedException("Album already defined: " + createAlbumCommand.artistId() + " or " + createAlbumCommand.name());
        });

        final UUID randomId = UUID.randomUUID();
        final Album album = Album.create(randomId, createAlbumCommand.name(), createAlbumCommand.artistId(), new HashSet<>());
        albumRepository.save(album);
        return album.events();
    }

    public List<AlbumEvent> insertTrack(InsertTrackCommand insertTrackCommand) {
        Optional<Album> album = albumRepository.findById(insertTrackCommand.id());
        return album.map(value -> processInsertingTrack(value, insertTrackCommand)).orElseGet(ArrayList::new);
    }

    private List<AlbumEvent> processInsertingTrack(Album album, InsertTrackCommand insertTrackCommand) {
        album.addTrack(insertTrackCommand.track());
        albumRepository.save(album);
        return album.events();
    }
}
