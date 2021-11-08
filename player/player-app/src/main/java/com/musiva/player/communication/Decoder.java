package com.musiva.player.communication;

import java.util.Optional;

public interface Decoder<S, D> {
    Optional<D> decode(S s);
}