package com.dratify.domain.songs.song.event;

import com.dratify.domain.songs.song.vo.ListeningCounter;
import com.dratify.domain.songs.song.vo.SongDataPath;
import java.time.Instant;
import java.util.UUID;

public record SongCreatedEvent(Instant occurredOn, UUID aggregateId, String name, SongDataPath songDataPath, ListeningCounter listeningCounter) implements SongEvent {

    public SongCreatedEvent(UUID songId, String name, SongDataPath songDataPath, ListeningCounter listeningCounter) {
        this(Instant.now(), songId, name, songDataPath, listeningCounter);
    }
}
