package com.musiva.albums.library;

import com.musiva.albums.library.commands.CreateAlbumLibraryCommand;
import com.musiva.albums.library.commands.InsertAlbumCommand;
import com.musiva.albums.library.event.AlbumLibraryEvent;
import com.musiva.albums.library.exception.AlbumLibraryAlreadyDefined;

import java.util.*;


public class AlbumLibraryFacade {

    private final AlbumLibraryRepository albumLibraryRepository;

    public AlbumLibraryFacade(AlbumLibraryRepository albumLibraryRepository) {
        this.albumLibraryRepository = albumLibraryRepository;
    }

    public List<AlbumLibraryEvent> createAlbumLibrary(CreateAlbumLibraryCommand createAlbumLibraryCommand) {
        final Optional<AlbumLibrary> albumLibrary = albumLibraryRepository.findByUser(createAlbumLibraryCommand.user());

        if(albumLibrary.isPresent()) {
            throw new AlbumLibraryAlreadyDefined("AlbumLibrary '" + createAlbumLibraryCommand.user() + "' already defined");
        }

        final UUID randomId = UUID.randomUUID();
        final AlbumLibrary created = AlbumLibrary.create(randomId, createAlbumLibraryCommand.user(), new HashSet<>());
        albumLibraryRepository.save(created);
        return created.events();
    }

    public List<AlbumLibraryEvent> insertAlbumLibrary(InsertAlbumCommand insertAlbumCommand) {
        final Optional<AlbumLibrary> albumLibrary = albumLibraryRepository.findByUser(insertAlbumCommand.userId());
        return albumLibrary.map(library -> processInsertingAlbum(library, insertAlbumCommand)).orElseGet(ArrayList::new);
    }

    private List<AlbumLibraryEvent> processInsertingAlbum(AlbumLibrary album, InsertAlbumCommand insertAlbumCommand) {
        album.addAlbum(insertAlbumCommand.album());
        albumLibraryRepository.save(album);
        return album.events();
    }
}
