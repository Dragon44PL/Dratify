package com.musiva.player.repository.cache;

public record ByteRange(long begin, long end) {

    public static ByteRange maximum() {
        return new ByteRange(0, Long.MAX_VALUE);
    }

}
