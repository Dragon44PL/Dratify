package com.musiva.player.communication;

import com.musiva.player.TrackData;

import java.util.Optional;

public interface Decoder<T> {
    Optional<TrackData> decode(T t);
}