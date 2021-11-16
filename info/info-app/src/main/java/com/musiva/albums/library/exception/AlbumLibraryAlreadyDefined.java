package com.musiva.albums.library.exception;

public class AlbumLibraryAlreadyDefined extends RuntimeException {

    public AlbumLibraryAlreadyDefined() {
        super();
    }

    public AlbumLibraryAlreadyDefined(String message) {
        super(message);
    }
}
