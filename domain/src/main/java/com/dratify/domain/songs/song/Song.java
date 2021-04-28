package com.dratify.domain.songs.song;

import com.dratify.domain.songs.song.vo.ListeningCounter;
import com.dratify.domain.songs.song.vo.SongDataPath;
import java.util.UUID;

class Song {

    private final UUID id;
    private final SongDataPath path;
    private String name;
    private ListeningCounter listeningCounter;

    Song(UUID id, SongDataPath path, String name) {
        this(id, path, name, ListeningCounter.empty());
    }

    Song(UUID id, SongDataPath path, String name, ListeningCounter listeningCounter) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.listeningCounter = listeningCounter;
    }

    void songListened() {
        this.listeningCounter = listeningCounter.increment();
    }

}
