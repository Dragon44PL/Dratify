package com.musiva.player;

import java.time.Instant;

public class TrackDataPlaceholder {

    private Instant occurredOn;
    private final TrackId trackId;
    private final TrackData trackData;

    public TrackDataPlaceholder(TrackId trackId, TrackData trackData) {
        this.occurredOn = Instant.now();
        this.trackId = trackId;
        this.trackData = trackData;
    }

    public TrackDataPlaceholder(Instant occurredOn, TrackId trackId, TrackData trackData) {
        this.occurredOn = occurredOn;
        this.trackId = trackId;
        this.trackData = trackData;
    }

    public void triggerRequested() {
        this.occurredOn = Instant.now();
    }

    public Instant occurredOn() {
        return occurredOn;
    }

    public TrackId trackId() {
        return trackId;
    }

    public TrackData trackData() {
        return trackData;
    }
}