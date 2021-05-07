package com.dratify.domain.songs.track.exception;

public class NegativeCounterException extends RuntimeException {

    public NegativeCounterException() {
        super();
    }

    public NegativeCounterException(String message) {
        super(message);
    }
}
