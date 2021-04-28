package com.dratify.domain.songs.song.vo;

import com.dratify.domain.songs.song.exception.NegativeCounterException;

public record ListeningCounter(long count) {

    private static final int MIN_COUNT = 0;

    public ListeningCounter {
        if(!moreThanMinimal(count)) {
            throw new NegativeCounterException();
        }
    }

    public static ListeningCounter empty() {
        return new ListeningCounter(MIN_COUNT);
    }

    public ListeningCounter increment() {
        final long increment =  count + 1;
        return new ListeningCounter(increment);
    }

    public static boolean moreThanMinimal(long count) {
        return count >= MIN_COUNT;
    }
}
