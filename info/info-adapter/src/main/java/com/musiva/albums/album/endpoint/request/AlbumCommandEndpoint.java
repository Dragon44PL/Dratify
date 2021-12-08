package com.musiva.albums.album.endpoint.request;

import com.musiva.albums.album.AlbumFacade;
import com.musiva.albums.album.commands.CreateAlbumCommand;
import com.musiva.albums.album.commands.InsertTrackCommand;
import com.musiva.albums.album.endpoint.request.dto.CreateAlbumRequest;
import com.musiva.albums.album.endpoint.request.dto.InsertTrackToAlbumRequest;
import com.musiva.albums.album.event.AlbumEvent;
import com.musiva.albums.album.vo.ArtistId;
import com.musiva.albums.album.vo.TrackId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/api/album")
public class AlbumCommandEndpoint {

    private final AlbumFacade albumFacade;

    public AlbumCommandEndpoint(AlbumFacade albumFacade) {
        this.albumFacade = albumFacade;
    }

    @PostMapping
    ResponseEntity<?> createAlbum(@RequestBody CreateAlbumRequest createAlbumRequest) {
        final UUID artistId = UUID.fromString(createAlbumRequest.getArtistId());
        final CreateAlbumCommand createAlbumCommand = new CreateAlbumCommand(createAlbumRequest.getName(), new ArtistId(artistId));
        final List<AlbumEvent> albumEvents = albumFacade.createAlbum(createAlbumCommand);
        return createAlbumResponse(albumEvents);
    }

    @PostMapping("/{id}/track")
    ResponseEntity<?> insertTrack(@PathVariable String id, @RequestBody InsertTrackToAlbumRequest insertTrackToAlbumRequest) {
        final TrackId trackId = new TrackId(UUID.fromString(insertTrackToAlbumRequest.getId()));
        final InsertTrackCommand insertTrackCommand = new InsertTrackCommand(UUID.fromString(id),trackId);
        final List<AlbumEvent> albumEvents = albumFacade.insertTrack(insertTrackCommand);
        return insertTrackResponse(albumEvents, trackId);
    }

    private ResponseEntity<?> insertTrackResponse(List<AlbumEvent> albumEvents, TrackId trackId) {
        final Optional<AlbumEvent> any = albumEvents.stream().findAny();
        return any.map(albumEvent -> ResponseEntity.created(URI.create("/api/album/" + albumEvent.aggregateId() + "/track/" + trackId.id())).build())
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    private ResponseEntity<?> createAlbumResponse(List<AlbumEvent> albumEvents) {
        final Optional<AlbumEvent> any = albumEvents.stream().findAny();
        return any.map(albumEvent -> ResponseEntity.created(URI.create("/api/album/" + albumEvent.aggregateId())).build())
                  .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
