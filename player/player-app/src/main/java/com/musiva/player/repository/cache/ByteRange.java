package com.musiva.player.repository.cache;

public record ByteRange(long begin, long end) {

    long size() {
        return end - begin;
    }
}
