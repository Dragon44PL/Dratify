package com.dratify.domain.songs.song;

import com.dratify.domain.songs.song.event.SongCreatedEvent;
import com.dratify.domain.songs.song.event.SongEvent;
import com.dratify.domain.songs.song.event.SongListenedEvent;
import com.dratify.domain.songs.song.vo.ListeningCounter;
import com.dratify.domain.songs.song.vo.SongDataPath;
import domain.AggregateRoot;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Song extends AggregateRoot<UUID, SongEvent> {

    private final UUID id;
    private final SongDataPath songDataPath;
    private final String name;
    private ListeningCounter listeningCounter;

    static Song create(UUID id, String name, SongDataPath path) {
        final Song song = new Song(id, name, path, ListeningCounter.empty(), new ArrayList<>());
        song.registerEvent(new SongCreatedEvent(song.id, song.name, song.songDataPath, song.listeningCounter));
        return song;
    }

    static Song restore(UUID id, String name, SongDataPath path, ListeningCounter listeningCounter) {
        return new Song(id, name, path, listeningCounter, new ArrayList<>());
    }

    private Song(UUID id, String name, SongDataPath songDataPath, ListeningCounter listeningCounter, List<SongEvent> events) {
        super(events);
        this.id = id;
        this.name = name;
        this.songDataPath = songDataPath;
        this.listeningCounter = listeningCounter;
    }

    void songListened() {
        this.listeningCounter = listeningCounter.increment();
        final SongListenedEvent songListenedEvent = new SongListenedEvent(id, listeningCounter);
        registerEvent(songListenedEvent);
    }

}
