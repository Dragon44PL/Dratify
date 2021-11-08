package com.musiva.player.communication;

import java.util.Optional;

public interface Encoder<S, D> {
    Optional<D> encode(S s);
}