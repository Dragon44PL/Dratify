package com.dratify.albums.library;

import com.dratify.albums.library.event.AlbumAddedEvent;
import com.dratify.albums.library.event.AlbumLibraryCreatedEvent;
import com.dratify.albums.library.event.AlbumLibraryEvent;
import com.dratify.albums.library.event.AlbumRemovedEvent;
import com.dratify.albums.library.vo.AlbumId;
import com.dratify.albums.library.vo.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AlbumLibraryTest {

    private final UUID ALBUM_LIBRARY_ID = UUID.randomUUID();
    private final UserId DEFAULT_OWNER = new UserId(UUID.randomUUID());
    private final AlbumId DEFAULT_ALBUM = new AlbumId(UUID.randomUUID());
    private final AlbumId ANOTHER_ALBUM = new AlbumId(UUID.randomUUID());

    /**
     *  AlbumLibrary Events
     */

    private final Class<AlbumLibraryCreatedEvent> ALBUM_LIBRARY_CREATED_EVENT = AlbumLibraryCreatedEvent.class;
    private final Class<AlbumAddedEvent> ALBUM_ADDED_EVENT = AlbumAddedEvent.class;
    private final Class<AlbumRemovedEvent> ALBUM_REMOVED_EVENT = AlbumRemovedEvent.class;

    @Test
    @DisplayName("Album Library Should Create Properly And Generate AlbumLibraryCreatedEvent")
    void albumShouldCreateProperlyAndGenerateEvent() {
        final Set<AlbumId> ALBUMS = Set.of(DEFAULT_ALBUM);
        final AlbumLibrary albumLibrary = AlbumLibrary.create(ALBUM_LIBRARY_ID, DEFAULT_OWNER, ALBUMS);

        Optional<AlbumLibraryEvent> albumEvent = albumLibrary.findLatestEvent();
        Assertions.assertTrue(albumEvent.isPresent());
        assertEquals(ALBUM_LIBRARY_CREATED_EVENT, albumEvent.get().getClass());

        final AlbumLibraryCreatedEvent albumLibraryCreatedEvent = (AlbumLibraryCreatedEvent) albumEvent.get();
        assertEquals(ALBUM_LIBRARY_ID, albumLibraryCreatedEvent.aggregateId());
        assertEquals(DEFAULT_OWNER, albumLibraryCreatedEvent.user());
        assertEquals(ALBUMS, albumLibraryCreatedEvent.albums());
    }

    @Test
    @DisplayName("Album Library Should Restore Properly And Not Generate Event")
    void albumShouldRestoreProperlyAndNotGenerateEvent() {
        final AlbumLibrary albumLibrary = AlbumLibrary.restore(ALBUM_LIBRARY_ID, DEFAULT_OWNER, Set.of(DEFAULT_ALBUM));

        Optional<AlbumLibraryEvent> albumEvent = albumLibrary.findLatestEvent();
        Assertions.assertFalse(albumEvent.isPresent());
    }

    @Test
    @DisplayName("Album Should Be Added To Library When Not Appear To Already Be")
    void albumShouldBeAddedWhenNotAdded() {
        final AlbumLibrary albumLibrary = AlbumLibrary.restore(ALBUM_LIBRARY_ID, DEFAULT_OWNER, Set.of(DEFAULT_ALBUM));

        assertFalse(albumLibrary.hasAlbum(ANOTHER_ALBUM));
        albumLibrary.addAlbum(ANOTHER_ALBUM);

        Optional<AlbumLibraryEvent> albumEvent = albumLibrary.findLatestEvent();
        Assertions.assertTrue(albumEvent.isPresent());
        assertEquals(ALBUM_ADDED_EVENT, albumEvent.get().getClass());

        final AlbumAddedEvent trackAddedEvent = (AlbumAddedEvent) albumEvent.get();
        assertEquals(trackAddedEvent.aggregateId(), ALBUM_LIBRARY_ID);
        assertEquals(trackAddedEvent.album(), ANOTHER_ALBUM);
    }

    @Test
    @DisplayName("Album Should Not Be Added To Library When Already Added")
    void albumShouldNotBeAddedAgain() {
        final AlbumLibrary albumLibrary = AlbumLibrary.restore(ALBUM_LIBRARY_ID, DEFAULT_OWNER, Set.of(DEFAULT_ALBUM));

        assertTrue(albumLibrary.hasAlbum(DEFAULT_ALBUM));
        albumLibrary.addAlbum(DEFAULT_ALBUM);

        Optional<AlbumLibraryEvent> albumEvent = albumLibrary.findLatestEvent();
        Assertions.assertFalse(albumEvent.isPresent());
    }

    @Test
    @DisplayName("Album Should Be Removed From Library When Already Added")
    void albumShouldBeRemovedTWhenAdded() {
        final AlbumLibrary albumLibrary = AlbumLibrary.restore(ALBUM_LIBRARY_ID, DEFAULT_OWNER, Set.of(DEFAULT_ALBUM));

        assertTrue(albumLibrary.hasAlbum(DEFAULT_ALBUM));
        albumLibrary.removeAlbum(DEFAULT_ALBUM);

        Optional<AlbumLibraryEvent> albumEvent = albumLibrary.findLatestEvent();
        Assertions.assertTrue(albumEvent.isPresent());
        assertEquals(ALBUM_REMOVED_EVENT, albumEvent.get().getClass());

        final AlbumRemovedEvent albumRemovedEvent = (AlbumRemovedEvent) albumEvent.get();
        assertEquals(albumRemovedEvent.aggregateId(), ALBUM_LIBRARY_ID);
        assertEquals(albumRemovedEvent.album(), DEFAULT_ALBUM);
    }

    @Test
    @DisplayName("Album Should Not Be Removed From Library When Not Added")
    void albumShouldNotBeRemovedWhenNotAdded() {
        final AlbumLibrary albumLibrary = AlbumLibrary.restore(ALBUM_LIBRARY_ID, DEFAULT_OWNER, Set.of(DEFAULT_ALBUM));

        assertFalse(albumLibrary.hasAlbum(ANOTHER_ALBUM));
        albumLibrary.removeAlbum(ANOTHER_ALBUM);

        final Optional<AlbumLibraryEvent> albumLibraryEvent = albumLibrary.findLatestEvent();
        Assertions.assertFalse(albumLibraryEvent.isPresent());
    }

}
