package com.musiva.albums.library;

import com.musiva.albums.library.commands.CreateAlbumLibraryCommand;
import com.musiva.albums.library.commands.InsertAlbumCommand;
import com.musiva.albums.library.event.AlbumLibraryEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumLibraryFacadeProxy extends AlbumLibraryFacade {

    public AlbumLibraryFacadeProxy(AlbumLibraryRepository albumLibraryRepository) {
        super(albumLibraryRepository);
    }

    @Override
    @Transactional
    public List<AlbumLibraryEvent> createAlbumLibrary(CreateAlbumLibraryCommand createAlbumLibraryCommand) {
        return super.createAlbumLibrary(createAlbumLibraryCommand);
    }

    @Override
    @Transactional
    public List<AlbumLibraryEvent> insertAlbumLibrary(InsertAlbumCommand insertAlbumCommand) {
        return super.insertAlbumLibrary(insertAlbumCommand);
    }
}
