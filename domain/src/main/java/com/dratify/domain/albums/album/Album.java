package com.dratify.domain.albums.album;

import com.dratify.domain.albums.album.vo.SongId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class Album {

    private final UUID id;
    private final Set<SongId> songs;

    Album(UUID id, Set<SongId> songs) {
        this.id = id;
        this.songs = new HashSet<>(songs);
    }

    Album(UUID id) {
        this.id = id;
        this.songs = new HashSet<>();
    }

    void addSong(SongId song) {
        if(!hasSong(song)) {
            songs.add(song);
        }
    }

    boolean hasSong(SongId song) {
        return songs.contains(song);
    }

    void removeSong(SongId song) {
        songs.removeIf(this::hasSong);
    }
}
