package com.musiva.tracks.track.exception;

public class NegativeCounterException extends RuntimeException {

    public NegativeCounterException() {
        super();
    }

    public NegativeCounterException(String message) {
        super(message);
    }
}
