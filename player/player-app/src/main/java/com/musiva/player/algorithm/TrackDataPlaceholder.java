package com.musiva.player.algorithm;

import com.musiva.player.TrackData;
import com.musiva.player.repository.Filename;

import java.time.Instant;

public class TrackDataPlaceholder<T> {

    private Instant occurredOn;
    private final Filename filename;
    private final T trackData;

    public TrackDataPlaceholder(Filename filename, T trackData) {
        this.occurredOn = Instant.now();
        this.filename = filename;
        this.trackData = trackData;
    }

    public TrackDataPlaceholder(Instant instant, Filename filename, T trackData) {
        this.occurredOn = instant;
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

    public T trackData() {
        return trackData;
    }
}