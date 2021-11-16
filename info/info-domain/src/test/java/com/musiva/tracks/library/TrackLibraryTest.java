package com.musiva.tracks.library;

import com.musiva.domain.tracks.library.event.TrackAddedEvent;
import com.musiva.domain.tracks.library.event.TrackLibraryCreatedEvent;
import com.musiva.domain.tracks.library.event.TrackLibraryEvent;
import com.musiva.domain.tracks.library.event.TrackRemovedEvent;
import com.musiva.domain.tracks.library.vo.TrackId;
import com.musiva.domain.tracks.library.vo.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TrackLibraryTest {

    private final UUID TRACK_LIBRARY_ID = UUID.randomUUID();
    private final UserId EXAMPLE_USER_ID = new UserId(UUID.randomUUID());
    private final TrackId EXAMPLE_TRACK_ID = new TrackId(UUID.randomUUID());

    /*
        TrackLibrary Events
     */

    Class<TrackLibraryCreatedEvent> TRACK_LIBRARY_CREATE_EVENT = TrackLibraryCreatedEvent.class;
    Class<TrackAddedEvent> TRACK_ADDED_EVENT = TrackAddedEvent.class;
    Class<TrackRemovedEvent> TRACK_REMOVED_EVENT = TrackRemovedEvent.class;

    @Test
    @DisplayName("TrackLibrary Should Create Properly And Generate TrackLibraryCreatedEvent")
    void trackLibraryShouldCreateProperlyAndGenerateEvent() {
        final Set<TrackId> tracks = Set.of(EXAMPLE_TRACK_ID);
        final TrackLibrary trackLibrary = TrackLibrary.create(TRACK_LIBRARY_ID, EXAMPLE_USER_ID, tracks);

        Optional<TrackLibraryEvent> trackLibraryEvent = trackLibrary.findLatestEvent();
        assertTrue(trackLibraryEvent.isPresent());
        assertEquals(TRACK_LIBRARY_CREATE_EVENT, trackLibraryEvent.get().getClass());

        final TrackLibraryCreatedEvent trackLibraryCreatedEvent = (TrackLibraryCreatedEvent) trackLibraryEvent.get();
        assertEquals(TRACK_LIBRARY_ID, trackLibraryCreatedEvent.aggregateId());
        assertEquals(EXAMPLE_USER_ID, trackLibraryCreatedEvent.user());
        assertEquals(tracks, trackLibraryCreatedEvent.tracks());
    }

    @Test
    @DisplayName("TrackLibrary Should Restore Properly And Not Generate Event")
    void trackLibraryShouldRestoreProperlyAndNotGenerateEvent() {
        final TrackLibrary trackLibrary = TrackLibrary.restore(TRACK_LIBRARY_ID, EXAMPLE_USER_ID, Set.of());


        Optional<TrackLibraryEvent> trackLibraryEvent = trackLibrary.findLatestEvent();
        assertFalse(trackLibraryEvent.isPresent());
    }

    @Test
    @DisplayName("Track Should Be Added To TrackLibrary When Not Appear To Already Be")
    void trackShouldBeAddedWhenNotAdded() {
        final Set<TrackId> tracks = Set.of();
        final TrackLibrary trackLibrary = TrackLibrary.restore(TRACK_LIBRARY_ID, EXAMPLE_USER_ID, tracks);
        trackLibrary.addTrack(EXAMPLE_TRACK_ID);

        Optional<TrackLibraryEvent> trackLibraryEvent = trackLibrary.findLatestEvent();
        assertTrue(trackLibraryEvent.isPresent());
        assertEquals(TRACK_ADDED_EVENT, trackLibraryEvent.get().getClass());

        final TrackAddedEvent trackAddedEvent = (TrackAddedEvent) trackLibraryEvent.get();
        assertEquals(TRACK_LIBRARY_ID, trackAddedEvent.aggregateId());
        assertEquals(EXAMPLE_TRACK_ID, trackAddedEvent.track());
    }

    @Test
    @DisplayName("Track Should Not Be Added To TrackLibrary When Already Added")
    void trackShouldNotBeAddedAgain() {
        final Set<TrackId> tracks = Set.of(EXAMPLE_TRACK_ID);
        final TrackLibrary trackLibrary = TrackLibrary.restore(TRACK_LIBRARY_ID, EXAMPLE_USER_ID, tracks);
        trackLibrary.addTrack(EXAMPLE_TRACK_ID);

        Optional<TrackLibraryEvent> trackLibraryEvent = trackLibrary.findLatestEvent();
        assertFalse(trackLibraryEvent.isPresent());
    }

    @Test
    @DisplayName("Track Should Be Removed From TrackLibrary When Added")
    void trackShouldBeRemovedWhenAdded() {
        final Set<TrackId> tracks = Set.of(EXAMPLE_TRACK_ID);
        final TrackLibrary trackLibrary = TrackLibrary.restore(TRACK_LIBRARY_ID, EXAMPLE_USER_ID, tracks);
        trackLibrary.removeTrack(EXAMPLE_TRACK_ID);

        Optional<TrackLibraryEvent> trackLibraryEvent = trackLibrary.findLatestEvent();
        assertTrue(trackLibraryEvent.isPresent());
        assertEquals(TRACK_REMOVED_EVENT, trackLibraryEvent.get().getClass());

        final TrackRemovedEvent trackRemovedEvent = (TrackRemovedEvent) trackLibraryEvent.get();
        assertEquals(TRACK_LIBRARY_ID, trackRemovedEvent.aggregateId());
        assertEquals(EXAMPLE_TRACK_ID, trackRemovedEvent.track());
    }

    @Test
    @DisplayName("Track Should Not Be Removed From TrackLibrary When Not Added")
    void trackShouldNotBeRemovedWhenNotAdded() {
        final Set<TrackId> tracks = Set.of();
        final TrackLibrary trackLibrary = TrackLibrary.restore(TRACK_LIBRARY_ID, EXAMPLE_USER_ID, tracks);
        trackLibrary.removeTrack(EXAMPLE_TRACK_ID);

        Optional<TrackLibraryEvent> trackLibraryEvent = trackLibrary.findLatestEvent();
        assertFalse(trackLibraryEvent.isPresent());
    }

}