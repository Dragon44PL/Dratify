package com.musiva.artists.exception;

public class ArtistAlreadyExistsException extends RuntimeException {

    public ArtistAlreadyExistsException() {
        super();
    }

    public ArtistAlreadyExistsException(String name) {
        super(name);
    }
}
