package com.musiva.player.communication;

import com.musiva.player.TrackData;

import java.util.Optional;

public interface Encoder<T> {
    Optional<T> encode(TrackData trackData);
}