package com.musiva.playlists.endpoint.request;

import com.musiva.playlists.PlaylistFacade;
import com.musiva.playlists.commands.InsertCollaborateCommand;
import com.musiva.playlists.commands.InsertTrackCommand;
import com.musiva.playlists.endpoint.request.dto.CreatePlaylistRequest;
import com.musiva.playlists.event.PlaylistEvent;
import com.musiva.playlists.commands.CreatePlaylistCommand;
import com.musiva.playlists.vo.TrackId;
import com.musiva.playlists.vo.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/api/playlist")
public class PlaylistCommandEndpoint {

    private final PlaylistFacade playlistFacade;

    public PlaylistCommandEndpoint(PlaylistFacade playlistFacade) {
        this.playlistFacade = playlistFacade;
    }

    @PostMapping
    ResponseEntity<?> createPlaylist(@RequestBody CreatePlaylistRequest createPlaylistRequest) {
        final UserId authorId = new UserId(UUID.fromString(createPlaylistRequest.getAuthorId()));
        final CreatePlaylistCommand createPlaylistCommand = new CreatePlaylistCommand(createPlaylistRequest.getName(),authorId);
        final List<PlaylistEvent> events = playlistFacade.createPlaylist(createPlaylistCommand);
        return createPlaylistResponse(events);
    }

    @PostMapping("/{id}/tracks/{trackId}")
    ResponseEntity<?> insertTrack(@PathVariable String id, @PathVariable String trackId) {
        final InsertTrackCommand insertTrackCommand = new InsertTrackCommand(UUID.fromString(id), new TrackId(UUID.fromString(trackId)));
        final List<PlaylistEvent> events = playlistFacade.insertTrack(insertTrackCommand);
        return createPlaylistResponse(events);
    }

    @PostMapping("/{id}/collaborators/{collaboratorId}")
    ResponseEntity<?> insertCollaborator(@PathVariable String id, @PathVariable String collaboratorId) {
        final InsertCollaborateCommand insertCollaborateCommand = new InsertCollaborateCommand(UUID.fromString(id), new UserId(UUID.fromString(collaboratorId)));
        final List<PlaylistEvent> events = playlistFacade.insertCollaborator(insertCollaborateCommand);
        return createPlaylistResponse(events);
    }

    private ResponseEntity<?> createPlaylistResponse(List<PlaylistEvent> events) {
        final Optional<PlaylistEvent> playlistEvent = events.stream().findAny();
        return playlistEvent
                .map(event -> ResponseEntity.created(URI.create("/api/playlist/" + event.aggregateId())).build())
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
