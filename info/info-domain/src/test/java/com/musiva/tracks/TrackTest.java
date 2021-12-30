package com.musiva.tracks;

import com.musiva.tracks.track.Track;
import com.musiva.tracks.track.event.TrackCreatedEvent;
import com.musiva.tracks.track.event.TrackDataPathChangedEvent;
import com.musiva.tracks.track.event.TrackEvent;
import com.musiva.tracks.track.event.TrackListenedEvent;
import com.musiva.tracks.track.vo.ListeningCounter;
import com.musiva.tracks.track.vo.TrackDataPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {

    private final UUID TRACK_ID = UUID.randomUUID();
    private final String TRACK_NAME = "Track";
    private final TrackDataPath TRACK_DATA_PATH = new TrackDataPath("path", "filename", "test");

    private final TrackDataPath ANOTHER_TRACK_DATA_PATH = new TrackDataPath("track", "data", "path");

    /*
        Track Events
     */
    Class<TrackCreatedEvent> TRACK_CREATED_EVENT = TrackCreatedEvent.class;
    Class<TrackListenedEvent> TRACK_LISTENED_EVENT = TrackListenedEvent.class;
    Class<TrackDataPathChangedEvent> TRACK_DATA_PATH_CHANGED_EVENT = TrackDataPathChangedEvent.class;

    @Test
    @DisplayName("Track Should Create Properly And Generate TrackCreatedEvent")
    void trackShouldCreateProperlyAndGenerateEvent() {
        final Track track = Track.create(TRACK_ID, TRACK_NAME, TRACK_DATA_PATH);

        Optional<TrackEvent> trackEvent = track.findLatestEvent();
        assertTrue(trackEvent.isPresent());
        assertEquals(TRACK_CREATED_EVENT, trackEvent.get().getClass());

        final TrackCreatedEvent trackCreatedEvent = (TrackCreatedEvent) trackEvent.get();
        assertEquals(TRACK_ID, trackCreatedEvent.aggregateId());
        assertEquals(TRACK_NAME, trackCreatedEvent.name());
        assertEquals(TRACK_DATA_PATH, trackCreatedEvent.trackDataPath());
        Assertions.assertEquals(0, trackCreatedEvent.listeningCounter().count());
    }

    @Test
    @DisplayName("Track Should Restore Properly And Not Generate Event")
    void trackShouldRestoreProperlyAndNotGenerateEvent() {
        final Track track = Track.restore(TRACK_ID, TRACK_NAME, TRACK_DATA_PATH, ListeningCounter.zero());

        Optional<TrackEvent> trackEvent = track.findLatestEvent();
        assertFalse(trackEvent.isPresent());
    }

    @Test
    @DisplayName("Track Should Be Added To TrackLibrary When Not Appear To Already Be")
    void trackShouldBeListenedAndGenerateEvent() {
        final Track track = Track.restore(TRACK_ID, TRACK_NAME, TRACK_DATA_PATH, ListeningCounter.zero());
        track.trackListened();

        Optional<TrackEvent> trackEvent = track.findLatestEvent();
        assertTrue(trackEvent.isPresent());
        assertEquals(TRACK_LISTENED_EVENT, trackEvent.get().getClass());

        final TrackListenedEvent trackListenedEvent = (TrackListenedEvent) trackEvent.get();
        assertEquals(TRACK_ID, trackListenedEvent.aggregateId());
        Assertions.assertEquals(1, trackListenedEvent.listeningCounter().count());
    }

    @Test
    @DisplayName("TrackDataPath Should Be Changed")
    void trackDataPathShouldBeChanged() {
        final Track track = Track.restore(TRACK_ID, TRACK_NAME, TRACK_DATA_PATH, ListeningCounter.zero());
        track.changeTrackDataPath(ANOTHER_TRACK_DATA_PATH);

        final Optional<TrackEvent> trackEvent = track.findLatestEvent();
        assertTrue(trackEvent.isPresent());
        assertEquals(TRACK_DATA_PATH_CHANGED_EVENT, trackEvent.get().getClass());

        final TrackDataPathChangedEvent trackDataPathChangedEvent = (TrackDataPathChangedEvent) trackEvent.get();
        assertEquals(trackDataPathChangedEvent.aggregateId(), TRACK_ID);
        assertEquals(trackDataPathChangedEvent.trackDataPath(), ANOTHER_TRACK_DATA_PATH);
    }

    @Test
    @DisplayName("TrackDataPath Should Not Be Changed")
    void trackDataPathShouldNotBeChanged() {
        final Track track = Track.restore(TRACK_ID, TRACK_NAME, TRACK_DATA_PATH, ListeningCounter.zero());
        track.changeTrackDataPath(TRACK_DATA_PATH);

        Optional<TrackEvent> trackEvent = track.findLatestEvent();
        assertFalse(trackEvent.isPresent());
    }
}
