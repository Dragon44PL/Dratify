package com.dratify.domain.albums.album;

import com.dratify.domain.albums.album.event.*;
import com.dratify.domain.albums.album.vo.ArtistId;
import com.dratify.domain.albums.album.vo.TrackId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    private final UUID ALBUM_ID = UUID.randomUUID();
    private final String ALBUM_NAME = "Test";
    private final UUID ARTIST_ID = UUID.randomUUID();
    private final UUID TRACK_ID = UUID.randomUUID();

    private final String ANOTHER_ALBUM_NAME = "Another";
    private final UUID ANOTHER_TRACK_ID = UUID.randomUUID();

    /**
     *  Album Events
     */

    private final Class<AlbumCreatedEvent> ALBUM_CREATED_EVENT = AlbumCreatedEvent.class;
    private final Class<AlbumNameChangedEvent> ALBUM_NAME_CHANGE_EVENT = AlbumNameChangedEvent.class;
    private final Class<TrackAddedEvent> TRACK_ADDED_EVENT = TrackAddedEvent.class;
    private final Class<TrackRemovedEvent> TRACK_REMOVED_EVENT = TrackRemovedEvent.class;

    @Test
    @DisplayName("Album Should Create Properly And Generate AlbumCreatedEvent")
    void albumShouldCreateProperlyAndGenerateEvent() {
        final Album album = Album.create(ALBUM_ID, ALBUM_NAME, new ArtistId(ARTIST_ID), Set.of(new TrackId(TRACK_ID)));
        Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        assertTrue(albumEvent.isPresent());
        assertEquals(ALBUM_CREATED_EVENT, albumEvent.get().getClass());
    }

    @Test
    @DisplayName("Album Should Restore Properly And Not Generate Event")
    void albumShouldRestoreProperlyAndNotGenerateEvent() {
        final Album album = Album.restore(ALBUM_ID, ALBUM_NAME, new ArtistId(ARTIST_ID), Set.of(new TrackId(TRACK_ID)));
        Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        assertFalse(albumEvent.isPresent());
    }

    @Test
    @DisplayName("Album Name Should Be Changed When Different")
    void albumNameShouldChangeAndGenerateEvent() {
        final Album album = Album.restore(ALBUM_ID, ALBUM_NAME, new ArtistId(ARTIST_ID), Set.of(new TrackId(TRACK_ID)));
        album.changeAlbumName(ANOTHER_ALBUM_NAME);
        Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        assertTrue(albumEvent.isPresent());
        assertEquals(ALBUM_NAME_CHANGE_EVENT, albumEvent.get().getClass());

        final AlbumNameChangedEvent albumNameChangedEvent = (AlbumNameChangedEvent) albumEvent.get();
        assertEquals(ALBUM_ID, albumNameChangedEvent.aggregateId());
        assertEquals(ANOTHER_ALBUM_NAME, albumNameChangedEvent.name());
    }

    @Test
    @DisplayName("Album Name Should Not Be Changed When Same")
    void albumNameShouldNotChangeAndNotGenerateEvent() {
        final Album album = Album.restore(ALBUM_ID, ALBUM_NAME, new ArtistId(ARTIST_ID), Set.of(new TrackId(TRACK_ID)));
        album.changeAlbumName(ALBUM_NAME);
        Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        assertFalse(albumEvent.isPresent());
    }

    @Test
    @DisplayName("Track Should Be Added To Album When Not Appear To Already Be")
    void trackShouldBeAddedToEventWhenNotAdded() {
        final Album album = Album.restore(ALBUM_ID, ALBUM_NAME, new ArtistId(ARTIST_ID), Set.of(new TrackId(TRACK_ID)));
        final TrackId track = new TrackId(ANOTHER_TRACK_ID);

        assertFalse(album.hasTrack(track));
        album.addTrack(track);
        final Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        assertTrue(albumEvent.isPresent());
        assertEquals(TRACK_ADDED_EVENT, albumEvent.get().getClass());
        final TrackAddedEvent trackAddedEvent = (TrackAddedEvent) albumEvent.get();
        assertEquals(trackAddedEvent.aggregateId(), ALBUM_ID);
        assertEquals(trackAddedEvent.track(), track);
    }

    @Test
    @DisplayName("Track Should Not Be Added To Album When Already Added")
    void trackShouldNotBeAddedToEventWhenAdded() {
        final Album album = Album.restore(ALBUM_ID, ALBUM_NAME, new ArtistId(ARTIST_ID), Set.of(new TrackId(TRACK_ID)));
        final TrackId track = new TrackId(TRACK_ID);

        assertTrue(album.hasTrack(track));
        album.addTrack(track);
        final Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        assertFalse(albumEvent.isPresent());
    }

    @Test
    @DisplayName("Track Should Be Removed From Album When Already Added")
    void trackShouldBeRemovedToEventWhenAdded() {
        final Album album = Album.restore(ALBUM_ID, ALBUM_NAME, new ArtistId(ARTIST_ID), Set.of(new TrackId(TRACK_ID)));
        final TrackId track = new TrackId(TRACK_ID);

        assertTrue(album.hasTrack(track));
        album.removeTrack(track);
        final Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        assertTrue(albumEvent.isPresent());
        assertEquals(TRACK_REMOVED_EVENT, albumEvent.get().getClass());
        final TrackRemovedEvent trackRemovedEvent = (TrackRemovedEvent) albumEvent.get();
        assertEquals(trackRemovedEvent.aggregateId(), ALBUM_ID);
        assertEquals(trackRemovedEvent.track(), track);
    }

    @Test
    @DisplayName("Track Should Not Be Removed To Album When Already Added")
    void trackShouldNotBeRemovedToEventWhenNotAdded() {
        final Album album = Album.restore(ALBUM_ID, ALBUM_NAME, new ArtistId(ARTIST_ID), Set.of(new TrackId(TRACK_ID)));
        final TrackId track = new TrackId(ANOTHER_TRACK_ID);

        assertFalse(album.hasTrack(track));
        album.removeTrack(track);
        final Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        assertFalse(albumEvent.isPresent());
    }

}
