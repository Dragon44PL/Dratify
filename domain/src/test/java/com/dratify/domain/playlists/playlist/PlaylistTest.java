package com.dratify.domain.playlists.playlist;

import com.dratify.domain.playlists.playlist.event.*;
import com.dratify.domain.playlists.playlist.vo.TrackId;
import com.dratify.domain.playlists.playlist.vo.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PlaylistTest {

    private final UUID PLAYLIST_ID = UUID.randomUUID();
    private final String EXAMPLE_NAME = "Playlist name";
    private final UserId EXAMPLE_AUTHOR = new UserId(UUID.randomUUID());
    private final UserId EXAMPLE_COLLABORATOR = new UserId(UUID.randomUUID());
    private final TrackId EXAMPLE_TRACK = new TrackId(UUID.randomUUID());

    /**
     *  Playlist Events
     */

    private final Class<PlaylistCreatedEvent> PLAYLIST_CREATED_EVENT = PlaylistCreatedEvent.class;
    private final Class<CollaboratorAddedEvent> COLLABORATOR_ADDED_EVENT = CollaboratorAddedEvent.class;
    private final Class<CollaboratorRemovedEvent> COLLABORATOR_REMOVED_EVENT = CollaboratorRemovedEvent.class;
    private final Class<TrackAddedEvent> TRACK_ADDED_EVENT = TrackAddedEvent.class;
    private final Class<TrackRemovedEvent> TRACK_REMOVED_EVENT = TrackRemovedEvent.class;

    @Test
    @DisplayName("Playlist Should Create Properly And Generate PlaylistCreatedEvent")
    void playlistShouldCreateProperlyAndGenerateEvent() {
        final Playlist playlist = Playlist.create(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, Set.of(), Set.of(EXAMPLE_TRACK));

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertTrue(playlistEvent.isPresent());
        assertEquals(PLAYLIST_CREATED_EVENT, playlistEvent.get().getClass());

        final PlaylistCreatedEvent playlistCreatedEvent = (PlaylistCreatedEvent) playlistEvent.get();
        assertEquals(PLAYLIST_ID, playlistCreatedEvent.aggregateId());
        assertEquals(EXAMPLE_NAME, playlistCreatedEvent.name());
        assertEquals(EXAMPLE_AUTHOR, playlistCreatedEvent.author());
    }

    @Test
    @DisplayName("Playlist Should Restore Properly And Not Generate Event")
    void playlistShouldRestoreProperlyAndNotGenerateEvent() {
        final Playlist playlist = Playlist.restore(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, Set.of(), Set.of());

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertFalse(playlistEvent.isPresent());
    }

    @Test
    @DisplayName("Collaborator Should Be Added To Playlist When Not Appear To Already Be")
    void collaboratorShouldBeAddedWhenNotAdded() {
        final Playlist playlist = Playlist.restore(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, Set.of(), Set.of());

        assertFalse(playlist.hasCollaborator(EXAMPLE_COLLABORATOR));
        playlist.addCollaborator(EXAMPLE_COLLABORATOR);

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertTrue(playlistEvent.isPresent());
        assertEquals(COLLABORATOR_ADDED_EVENT, playlistEvent.get().getClass());

        final CollaboratorAddedEvent collaboratorAddedEvent = (CollaboratorAddedEvent) playlistEvent.get();
        assertEquals(PLAYLIST_ID, collaboratorAddedEvent.aggregateId());
        assertEquals(EXAMPLE_COLLABORATOR, collaboratorAddedEvent.collaborator());
    }

    @Test
    @DisplayName("Collaborator Should Not Be Added To Playlist When Already Added")
    void collaboratorShouldNotBeAddedAgain() {
        final Set<UserId> collaborators = Set.of(EXAMPLE_COLLABORATOR);
        final Playlist playlist = Playlist.restore(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, collaborators, Set.of(EXAMPLE_TRACK));

        assertTrue(playlist.hasCollaborator(EXAMPLE_COLLABORATOR));
        playlist.addCollaborator(EXAMPLE_COLLABORATOR);

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertFalse(playlistEvent.isPresent());
    }

    @Test
    @DisplayName("Collaborator Should Be Removed From Playlist When Added")
    void collaboratorShouldBeRemovedWhenAdded() {
        final Set<UserId> collaborators = Set.of(EXAMPLE_COLLABORATOR);
        final Playlist playlist = Playlist.restore(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, collaborators, Set.of(EXAMPLE_TRACK));

        assertTrue(playlist.hasCollaborator(EXAMPLE_COLLABORATOR));
        playlist.removeCollaborator(EXAMPLE_COLLABORATOR);

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertTrue(playlistEvent.isPresent());
        assertEquals(COLLABORATOR_REMOVED_EVENT, playlistEvent.get().getClass());

        final CollaboratorRemovedEvent collaboratorRemovedEvent = (CollaboratorRemovedEvent) playlistEvent.get();
        assertEquals(PLAYLIST_ID, collaboratorRemovedEvent.aggregateId());
        assertEquals(EXAMPLE_COLLABORATOR, collaboratorRemovedEvent.collaborator());
    }

    @Test
    @DisplayName("Collaborator Should Not Be Removed From Playlist When Not Added")
    void collaboratorShouldNotBeRemovedWhenNotAdded() {
        final Playlist playlist = Playlist.restore(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, Set.of(), Set.of());

        assertFalse(playlist.hasCollaborator(EXAMPLE_COLLABORATOR));
        playlist.removeCollaborator(EXAMPLE_COLLABORATOR);

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertFalse(playlistEvent.isPresent());
    }

    @Test
    @DisplayName("Track Should Be Added To Playlist When Not Appear To Already Be")
    void trackShouldBeAddedWhenNotAdded() {
        final Playlist playlist = Playlist.restore(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, Set.of(), Set.of());

        assertFalse(playlist.hasTrack(EXAMPLE_TRACK));
        playlist.addTrack(EXAMPLE_TRACK);

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertTrue(playlistEvent.isPresent());
        assertEquals(TRACK_ADDED_EVENT, playlistEvent.get().getClass());

        final TrackAddedEvent trackAddedEvent = (TrackAddedEvent) playlistEvent.get();
        assertEquals(PLAYLIST_ID, trackAddedEvent.aggregateId());
        assertEquals(EXAMPLE_TRACK, trackAddedEvent.track());
    }

    @Test
    @DisplayName("Track Should Not Be Added To Playlist When Already Added")
    void trackShouldNotBeAddedAgain() {
        final Playlist playlist = Playlist.restore(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, Set.of(), Set.of(EXAMPLE_TRACK));

        assertTrue(playlist.hasTrack(EXAMPLE_TRACK));
        playlist.addTrack(EXAMPLE_TRACK);

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertFalse(playlistEvent.isPresent());
    }

    @Test
    @DisplayName("Track Should Be Removed From Playlist When Added")
    void trackShouldBeRemovedWhenAdded() {
        final Playlist playlist = Playlist.restore(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, Set.of(), Set.of(EXAMPLE_TRACK));

        assertTrue(playlist.hasTrack(EXAMPLE_TRACK));
        playlist.removeTrack(EXAMPLE_TRACK);

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertTrue(playlistEvent.isPresent());
        assertEquals(TRACK_REMOVED_EVENT, playlistEvent.get().getClass());

        final TrackRemovedEvent trackRemovedEvent = (TrackRemovedEvent) playlistEvent.get();
        assertEquals(PLAYLIST_ID, trackRemovedEvent.aggregateId());
        assertEquals(EXAMPLE_TRACK, trackRemovedEvent.track());
    }

    @Test
    @DisplayName("Track Should Not Be Removed From Playlist When Not Added")
    void trackShouldNotBeRemovedWhenNotAdded() {
        final Playlist playlist = Playlist.restore(PLAYLIST_ID, EXAMPLE_NAME, EXAMPLE_AUTHOR, Set.of(), Set.of());

        assertFalse(playlist.hasTrack(EXAMPLE_TRACK));
        playlist.removeTrack(EXAMPLE_TRACK);

        Optional<PlaylistEvent> playlistEvent = playlist.findLatestEvent();
        assertFalse(playlistEvent.isPresent());
    }
}

