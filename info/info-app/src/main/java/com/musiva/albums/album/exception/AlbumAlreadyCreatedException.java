package com.musiva.albums.album.exception;

public class AlbumAlreadyCreatedException extends RuntimeException {

    public AlbumAlreadyCreatedException() {
        super();
    }

    public AlbumAlreadyCreatedException(String message) {
        super(message);
    }
}
