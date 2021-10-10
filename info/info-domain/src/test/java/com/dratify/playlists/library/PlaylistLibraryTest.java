package com.dratify.playlists.library;

import com.dratify.playlists.library.event.PlaylistAddedEvent;
import com.dratify.playlists.library.event.PlaylistLibraryCreatedEvent;
import com.dratify.playlists.library.event.PlaylistLibraryEvent;
import com.dratify.playlists.library.event.PlaylistRemovedEvent;
import com.dratify.playlists.library.vo.PlaylistId;
import com.dratify.playlists.library.vo.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PlaylistLibraryTest {

    private final UUID PLAYLIST_LIBRARY_ID = UUID.randomUUID();
    private final UserId EXAMPLE_USER = new UserId(UUID.randomUUID());
    private final PlaylistId EXAMPLE_PLAYLIST = new PlaylistId(UUID.randomUUID());

    /**
     *  PlaylistLibrary Events
     */

    private final Class<PlaylistLibraryCreatedEvent> PLAYLIST_LIBRARY_CREATED_EVENT = PlaylistLibraryCreatedEvent.class;
    private final Class<PlaylistAddedEvent> PLAYLIST_ADDED_EVENT = PlaylistAddedEvent.class;
    private final Class<PlaylistRemovedEvent> PLAYLIST_REMOVED_EVENT = PlaylistRemovedEvent.class;

    @Test
    @DisplayName("PlaylistLibrary Should Create Properly And Generate PlaylistLibraryCreatedEvent")
    void playlistLibraryShouldCreateProperlyAndGenerateEvent() {
        final Set<PlaylistId> playlists = Set.of(EXAMPLE_PLAYLIST);
        final PlaylistLibrary playlistLibrary = PlaylistLibrary.create(PLAYLIST_LIBRARY_ID, EXAMPLE_USER, playlists);

        Optional<PlaylistLibraryEvent> playlistLibraryEvent = playlistLibrary.findLatestEvent();
        Assertions.assertTrue(playlistLibraryEvent.isPresent());
        assertEquals(PLAYLIST_LIBRARY_CREATED_EVENT, playlistLibraryEvent.get().getClass());

        final PlaylistLibraryCreatedEvent playlistLibraryCreatedEvent = (PlaylistLibraryCreatedEvent) playlistLibraryEvent.get();
        assertEquals(PLAYLIST_LIBRARY_ID, playlistLibraryCreatedEvent.aggregateId());
        assertEquals(EXAMPLE_USER, playlistLibraryCreatedEvent.user());
        assertEquals(playlists, playlistLibraryCreatedEvent.playlists());
    }

    @Test
    @DisplayName("PlaylistLibrary Should Restore Properly And Not Generate Event")
    void playlistLibraryShouldRestoreProperlyAndNotGenerateEvent() {
        final Set<PlaylistId> playlists = Set.of(EXAMPLE_PLAYLIST);
        final PlaylistLibrary playlistLibrary = PlaylistLibrary.restore(PLAYLIST_LIBRARY_ID, EXAMPLE_USER, playlists);

        Optional<PlaylistLibraryEvent> playlistEvent = playlistLibrary.findLatestEvent();
        Assertions.assertFalse(playlistEvent.isPresent());
    }

    @Test
    @DisplayName("Playlist Should Be Added To Library When Not Appear To Already Be")
    void playlistShouldBeAddedWhenNotAdded() {
        final PlaylistLibrary playlistLibrary = PlaylistLibrary.restore(PLAYLIST_LIBRARY_ID, EXAMPLE_USER, Set.of());

        assertFalse(playlistLibrary.hasPlaylist(EXAMPLE_PLAYLIST));
        playlistLibrary.addPlaylist(EXAMPLE_PLAYLIST);

        Optional<PlaylistLibraryEvent> playlistLibraryEvent = playlistLibrary.findLatestEvent();
        Assertions.assertTrue(playlistLibraryEvent.isPresent());
        assertEquals(PLAYLIST_ADDED_EVENT, playlistLibraryEvent.get().getClass());

        final PlaylistAddedEvent playlistAddedEvent = (PlaylistAddedEvent) playlistLibraryEvent.get();
        assertEquals(PLAYLIST_LIBRARY_ID, playlistAddedEvent.aggregateId());
        assertEquals(EXAMPLE_PLAYLIST, playlistAddedEvent.playlist());
    }

    @Test
    @DisplayName("Playlist Should Not Be Added To Library When Already Added")
    void playlistShouldNotBeAddedAgain() {
        final PlaylistLibrary playlistLibrary = PlaylistLibrary.restore(PLAYLIST_LIBRARY_ID, EXAMPLE_USER, Set.of(EXAMPLE_PLAYLIST));

        assertTrue(playlistLibrary.hasPlaylist(EXAMPLE_PLAYLIST));
        playlistLibrary.addPlaylist(EXAMPLE_PLAYLIST);

        Optional<PlaylistLibraryEvent> playlistLibraryEvent = playlistLibrary.findLatestEvent();
        Assertions.assertFalse(playlistLibraryEvent.isPresent());
    }

    @Test
    @DisplayName("Playlist Should Be Removed From Library When Added")
    void playlistShouldBeRemovedWhenAdded() {
        final PlaylistLibrary playlistLibrary = PlaylistLibrary.restore(PLAYLIST_LIBRARY_ID, EXAMPLE_USER, Set.of(EXAMPLE_PLAYLIST));

        assertTrue(playlistLibrary.hasPlaylist(EXAMPLE_PLAYLIST));
        playlistLibrary.removePlaylist(EXAMPLE_PLAYLIST);

        Optional<PlaylistLibraryEvent> playlistLibraryEvent = playlistLibrary.findLatestEvent();
        Assertions.assertTrue(playlistLibraryEvent.isPresent());
        assertEquals(PLAYLIST_REMOVED_EVENT, playlistLibraryEvent.get().getClass());

        final PlaylistRemovedEvent playlistRemovedEvent = (PlaylistRemovedEvent) playlistLibraryEvent.get();
        assertEquals(PLAYLIST_LIBRARY_ID, playlistRemovedEvent.aggregateId());
        assertEquals(EXAMPLE_PLAYLIST, playlistRemovedEvent.playlist());
    }

    @Test
    @DisplayName("Playlist Should Not Be Removed From Library When Not Added")
    void playlistShouldNotBeRemovedWhenNotAdded() {
        final PlaylistLibrary playlistLibrary = PlaylistLibrary.restore(PLAYLIST_LIBRARY_ID, EXAMPLE_USER, Set.of());

        assertFalse(playlistLibrary.hasPlaylist(EXAMPLE_PLAYLIST));
        playlistLibrary.removePlaylist(EXAMPLE_PLAYLIST);

        Optional<PlaylistLibraryEvent> playlistLibraryEvent = playlistLibrary.findLatestEvent();
        Assertions.assertFalse(playlistLibraryEvent.isPresent());
    }
}
