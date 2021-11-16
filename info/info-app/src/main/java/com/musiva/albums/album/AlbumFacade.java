package com.musiva.albums.album;

import com.musiva.albums.album.commands.CreateAlbumCommand;
import com.musiva.albums.album.commands.InsertAlbumCommand;
import com.musiva.albums.album.event.AlbumEvent;
import com.musiva.albums.album.exception.AlbumAlreadyCreatedException;

import java.util.*;

public class AlbumFacade {

    private final AlbumRepository albumRepository;

    public AlbumFacade(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<AlbumEvent> createAlbum(CreateAlbumCommand createAlbumCommand) {
        Optional<Album> foundByName = albumRepository.findByName(createAlbumCommand.name());
        Optional<Album> foundByArtist = albumRepository.findByArtist(createAlbumCommand.artistId());

        if(foundByName.isPresent() && foundByArtist.isPresent()) {
            throw new AlbumAlreadyCreatedException("Album already defined: " + createAlbumCommand.artistId() + " or " + createAlbumCommand.name());
        }

        final UUID randomId = UUID.randomUUID();
        final Album album = Album.create(randomId, createAlbumCommand.name(), createAlbumCommand.artistId(), new HashSet<>());
        albumRepository.save(album);
        return album.events();
    }

    public List<AlbumEvent> insertTrack(InsertAlbumCommand insertAlbumCommand) {
        Optional<Album> album = albumRepository.findById(insertAlbumCommand.id());
        return album.map(value -> processInsertingTrack(value, insertAlbumCommand)).orElseGet(ArrayList::new);
    }

    private List<AlbumEvent> processInsertingTrack(Album album, InsertAlbumCommand insertAlbumCommand) {
        album.addTrack(insertAlbumCommand.track());
        albumRepository.save(album);
        return album.events();
    }
}
