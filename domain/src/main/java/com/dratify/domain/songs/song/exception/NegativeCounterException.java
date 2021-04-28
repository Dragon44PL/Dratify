package com.dratify.domain.songs.song.exception;

public class NegativeCounterException extends RuntimeException {

    public NegativeCounterException() {
        super();
    }

    public NegativeCounterException(String message) {
        super(message);
    }
}
