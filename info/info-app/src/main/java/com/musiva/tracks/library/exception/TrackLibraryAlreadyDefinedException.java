package com.musiva.tracks.library.exception;

public class TrackLibraryAlreadyDefinedException extends RuntimeException {

    public TrackLibraryAlreadyDefinedException() {
        super();
    }

    public TrackLibraryAlreadyDefinedException(String message) {
        super(message);
    }

}
