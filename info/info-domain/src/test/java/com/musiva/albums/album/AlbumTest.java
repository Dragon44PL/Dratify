package com.musiva.albums.album;

import com.musiva.albums.album.vo.ArtistId;
import com.musiva.albums.album.vo.TrackId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    private final UUID ALBUM_ID = UUID.randomUUID();
    private final String DEFAULT_ALBUM_NAME = "Test";
    private final ArtistId DEFAULT_ARTIST = new ArtistId(UUID.randomUUID());
    private final TrackId DEFAULT_TRACK = new TrackId(UUID.randomUUID());
    private final TrackId ANOTHER_TRACK = new TrackId(UUID.randomUUID());

    private final String ANOTHER_ALBUM_NAME = "Another";

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
        final Set<TrackId> TRACKS = Set.of(DEFAULT_TRACK);
        final Album album = Album.create(ALBUM_ID, DEFAULT_ALBUM_NAME, DEFAULT_ARTIST, TRACKS);

        Optional<AlbumEvent> albumEvent = album.findLatestEvent();
        Assertions.assertTrue(albumEvent.isPresent());
        assertEquals(ALBUM_CREATED_EVENT, albumEvent.get().getClass());

        final AlbumCreatedEvent albumCreatedEvent = (AlbumCreatedEvent) albumEvent.get();
        assertEquals(ALBUM_ID, albumCreatedEvent.aggregateId());
        assertEquals(DEFAULT_ALBUM_NAME, albumCreatedEvent.name());
        assertEquals(DEFAULT_ARTIST, albumCreatedEvent.artist());
        assertEquals(TRACKS, albumCreatedEvent.tracks());
    }

    @Test
    @DisplayName("Album Should Restore Properly And Not Generate Event")
    void albumShouldRestoreProperlyAndNotGenerateEvent() {
        final Album album = Album.restore(ALBUM_ID, DEFAULT_ALBUM_NAME, DEFAULT_ARTIST, Set.of(DEFAULT_TRACK));

        Optional<AlbumEvent> albumEvent = album.findLatestEvent();
        Assertions.assertFalse(albumEvent.isPresent());
    }

    @Test
    @DisplayName("Album Name Should Be Changed When Different")
    void albumNameShouldChangeAndGenerateEvent() {
        final Album album = Album.restore(ALBUM_ID, DEFAULT_ALBUM_NAME, DEFAULT_ARTIST, Set.of(DEFAULT_TRACK));
        album.changeAlbumName(ANOTHER_ALBUM_NAME);
        Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        Assertions.assertTrue(albumEvent.isPresent());
        assertEquals(ALBUM_NAME_CHANGE_EVENT, albumEvent.get().getClass());

        final AlbumNameChangedEvent albumNameChangedEvent = (AlbumNameChangedEvent) albumEvent.get();
        assertEquals(ALBUM_ID, albumNameChangedEvent.aggregateId());
        assertEquals(ANOTHER_ALBUM_NAME, albumNameChangedEvent.name());
    }

    @Test
    @DisplayName("Album Name Should Not Be Changed When Same")
    void albumNameShouldNotChangeAndNotGenerateEvent() {
        final Album album = Album.restore(ALBUM_ID, DEFAULT_ALBUM_NAME, DEFAULT_ARTIST, Set.of(DEFAULT_TRACK));
        album.changeAlbumName(DEFAULT_ALBUM_NAME);

        Optional<AlbumEvent> albumEvent = album.findLatestEvent();
        Assertions.assertFalse(albumEvent.isPresent());
    }

    @Test
    @DisplayName("Track Should Be Added To Album When Not Appear To Already Be")
    void trackShouldBeAddedWhenNotAdded() {
        final Album album = Album.restore(ALBUM_ID, DEFAULT_ALBUM_NAME, DEFAULT_ARTIST, Set.of(DEFAULT_TRACK));

        assertFalse(album.hasTrack(ANOTHER_TRACK));
        album.addTrack(ANOTHER_TRACK);

        final Optional<AlbumEvent> albumEvent = album.findLatestEvent();
        Assertions.assertTrue(albumEvent.isPresent());
        assertEquals(TRACK_ADDED_EVENT, albumEvent.get().getClass());

        final TrackAddedEvent trackAddedEvent = (TrackAddedEvent) albumEvent.get();
        assertEquals(trackAddedEvent.aggregateId(), ALBUM_ID);
        assertEquals(trackAddedEvent.track(), ANOTHER_TRACK);
    }

    @Test
    @DisplayName("Track Should Not Be Added To Album When Already Added")
    void trackShouldNotBeAddedAgain() {
        final Album album = Album.restore(ALBUM_ID, DEFAULT_ALBUM_NAME, DEFAULT_ARTIST, Set.of(DEFAULT_TRACK));

        assertTrue(album.hasTrack(DEFAULT_TRACK));
        album.addTrack(DEFAULT_TRACK);

        final Optional<AlbumEvent> albumEvent = album.findLatestEvent();
        Assertions.assertFalse(albumEvent.isPresent());
    }

    @Test
    @DisplayName("Track Should Be Removed From Album When Already Added")
    void trackShouldBeRemovedWhenAdded() {
        final Album album = Album.restore(ALBUM_ID, DEFAULT_ALBUM_NAME, DEFAULT_ARTIST, Set.of(DEFAULT_TRACK));

        assertTrue(album.hasTrack(DEFAULT_TRACK));
        album.removeTrack(DEFAULT_TRACK);
        final Optional<AlbumEvent> albumEvent = album.findLatestEvent();

        Assertions.assertTrue(albumEvent.isPresent());
        assertEquals(TRACK_REMOVED_EVENT, albumEvent.get().getClass());
        final TrackRemovedEvent trackRemovedEvent = (TrackRemovedEvent) albumEvent.get();
        assertEquals(trackRemovedEvent.aggregateId(), ALBUM_ID);
        assertEquals(trackRemovedEvent.track(), DEFAULT_TRACK);
    }

    @Test
    @DisplayName("Track Should Not Be Removed To Album When Already Added")
    void trackShouldNotBeRemovedWhenNotAdded() {
        final Album album = Album.restore(ALBUM_ID, DEFAULT_ALBUM_NAME, DEFAULT_ARTIST, Set.of(DEFAULT_TRACK));

        assertFalse(album.hasTrack(ANOTHER_TRACK));
        album.removeTrack(ANOTHER_TRACK);

        final Optional<AlbumEvent> albumEvent = album.findLatestEvent();
        Assertions.assertFalse(albumEvent.isPresent());
    }

}
