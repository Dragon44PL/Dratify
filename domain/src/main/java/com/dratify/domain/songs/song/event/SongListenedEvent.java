package com.dratify.domain.songs.song.event;

import com.dratify.domain.songs.song.vo.ListeningCounter;
import java.time.Instant;
import java.util.UUID;

public record SongListenedEvent(Instant occurredOn, UUID aggregateId, ListeningCounter listeningCounter) implements SongEvent {

    public SongListenedEvent(UUID songId, ListeningCounter listeningCounter) {
        this(Instant.now(), songId, listeningCounter);
    }
}
