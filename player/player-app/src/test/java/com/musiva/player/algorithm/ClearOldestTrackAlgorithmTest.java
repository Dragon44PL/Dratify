package com.musiva.player.algorithm;

import player.dto.TrackData;
import player.dto.TrackDataPlaceholder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearOldestTrackAlgorithmTest {

    private static ClearOldestTracksAlgorithm clearOldestTracksAlgorithm;

    /*
        Data
     */

    private static TrackDataPlaceholder LATEST;
    private static TrackDataPlaceholder MIDDLE;
    private static TrackDataPlaceholder OLDEST;

    @BeforeAll
    static void set() {
        clearOldestTracksAlgorithm = new ClearOldestTracksAlgorithm();
        LATEST = new TrackDataPlaceholder(Instant.ofEpochMilli(1000), new TrackId(UUID.randomUUID()), new TrackData(new byte[]{}));
        MIDDLE = new TrackDataPlaceholder(Instant.ofEpochMilli(100), new TrackId(UUID.randomUUID()), new TrackData(new byte[]{}));
        OLDEST = new TrackDataPlaceholder(Instant.ofEpochMilli(10), new TrackId(UUID.randomUUID()), new TrackData(new byte[]{}));
    }

    @Test
    @DisplayName("Should Remove Proper Amount Of Data")
    void shouldSortSetProperly() {
        final List<TrackDataPlaceholder> data = new ArrayList<>(List.of(LATEST, MIDDLE, OLDEST));
        clearOldestTracksAlgorithm.clearTrackData(data, 1);

        assertEquals(2, data.size());
        assertTrue(data.contains(MIDDLE));
        assertTrue(data.contains(LATEST));
    }

    @Test
    @DisplayName("Should Delete All When Requesting To Delete More Than Maximum")
    void shouldDeleteAllWhenMoreThanMaximumAmount() {
        final List<TrackDataPlaceholder> data = new ArrayList<>(List.of(LATEST, MIDDLE, OLDEST));
        clearOldestTracksAlgorithm.clearTrackData(data, 10);

        assertEquals(0, data.size());
    }

}
