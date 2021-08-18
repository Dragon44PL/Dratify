package com.dratify.domain.tracks.track.event;

import com.dratify.domain.tracks.track.vo.TrackDataPath;
import java.time.Instant;
import java.util.UUID;

public record TrackDataPathChangedEvent(Instant occurredOn, UUID aggregateId, TrackDataPath trackDataPath) implements TrackEvent {

    public TrackDataPathChangedEvent(UUID aggregateId, TrackDataPath trackDataPath) {
        this(Instant.now(), aggregateId, trackDataPath);
    }
}
