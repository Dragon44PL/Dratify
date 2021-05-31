package com.dratify.domain.tracks.track.event;

import com.dratify.domain.tracks.track.vo.TrackDataPath;
import java.time.Instant;
import java.util.UUID;

public record TrackDataPathChanged(Instant occurredOn, UUID aggregateId, TrackDataPath trackDataPath) implements TrackEvent {

    public TrackDataPathChanged(UUID aggregateId, TrackDataPath trackDataPath) {
        this(Instant.now(), aggregateId, trackDataPath);
    }
}
