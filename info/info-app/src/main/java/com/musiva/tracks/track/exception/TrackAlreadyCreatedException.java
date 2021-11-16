package com.musiva.tracks.track.exception;

public class TrackAlreadyCreatedException extends RuntimeException {

    public TrackAlreadyCreatedException() {
        super();
    }

    public TrackAlreadyCreatedException(String message) {
        super(message);
    }

}
