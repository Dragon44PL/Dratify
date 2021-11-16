package com.musiva.playlists.playlist.exception;

public class PlaylistAlreadyDefinedException extends RuntimeException {

    public PlaylistAlreadyDefinedException() {
        super();
    }

    public PlaylistAlreadyDefinedException(String message) {
        super(message);
    }
}
