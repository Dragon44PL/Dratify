package com.musiva.player.algorithm;

import com.musiva.player.TrackData;
import com.musiva.player.repository.Filename;

import java.time.Instant;

public class TrackDataPlaceholder {

    private Instant occurredOn;
    private final Filename filename;
    private final TrackData trackData;

    public TrackDataPlaceholder(Filename filename, TrackData trackData) {
        this.occurredOn = Instant.now();
        this.filename = filename;
        this.trackData = trackData;
    }

    public void triggerRequested() {
        this.occurredOn = Instant.now();
    }

    public Instant occurredOn() {
        return occurredOn;
    }

    public Filename filename() {
        return filename;
    }

    public TrackData trackData() {
        return trackData;
    }
}