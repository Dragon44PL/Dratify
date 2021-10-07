package com.dratify.tracks.track.vo;

import com.dratify.domain.tracks.track.exception.NegativeCounterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListeningCounterTest {

    private final long POSITIVE_VALUE = 10;
    private final long NEGATIVE_VALUE = -10;

    /*
        ListeningCounterException
     */

    private final Class<NegativeCounterException> NEGATIVE_COUNTER_EXCEPTION = NegativeCounterException.class;

    @Test
    @DisplayName("ListeningCounter Create Properly When Positive Value")
    void listeningCounterCreateProperlyWhenPositiveValue() {
        final ListeningCounter listeningCounter = new ListeningCounter(POSITIVE_VALUE);
        assertEquals(POSITIVE_VALUE, listeningCounter.count());
    }

    @Test
    @DisplayName("ListeningCounter Should Be Create Counter With Zero Value")
    void listeningCounterCreateZeroValue() {
        final ListeningCounter listeningCounter = ListeningCounter.zero();
        assertEquals(0, listeningCounter.count());
    }

    @Test
    @DisplayName("ListeningCounter Throws NegativeCounterException When Negative Value")
    void listeningCounterThrowsNegativeCounterException() {
        assertThrows(NEGATIVE_COUNTER_EXCEPTION, () -> new ListeningCounter(NEGATIVE_VALUE));
    }

    @Test
    @DisplayName("ListeningCounter Should Increment Value Properly")
    void listeningCounterShouldIncrementProperly() {
        final ListeningCounter listeningCounter = ListeningCounter.zero();
        final ListeningCounter incrementedCounter = listeningCounter.increment();
        assertEquals(listeningCounter.count() + 1, incrementedCounter.count());
    }
}