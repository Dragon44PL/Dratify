package com.musiva.playlists.exception;

public class PlaylistAlreadyDefinedException extends RuntimeException {

    public PlaylistAlreadyDefinedException() {
        super();
    }

    public PlaylistAlreadyDefinedException(String message) {
        super(message);
    }
}
