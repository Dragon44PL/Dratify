package com.musiva.albums.library.endpoint.request;

import com.musiva.albums.library.AlbumLibraryFacade;
import com.musiva.albums.library.commands.CreateAlbumLibraryCommand;
import com.musiva.albums.library.commands.InsertAlbumCommand;
import com.musiva.albums.library.event.AlbumLibraryEvent;
import com.musiva.albums.library.vo.AlbumId;
import com.musiva.albums.library.vo.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/api/user/albums")
public class AlbumLibraryCommandEndpoint {

    private final AlbumLibraryFacade albumLibraryFacade;

    public AlbumLibraryCommandEndpoint(AlbumLibraryFacade albumLibraryFacade) {
        this.albumLibraryFacade = albumLibraryFacade;
    }

    @PostMapping("/api/users/{userId}/album")
    ResponseEntity<?> createAlbumLibrary(@PathVariable String userId) {
        final CreateAlbumLibraryCommand createAlbumLibraryCommand = new CreateAlbumLibraryCommand(new UserId(UUID.fromString(userId)));
        final List<AlbumLibraryEvent> events = albumLibraryFacade.createAlbumLibrary(createAlbumLibraryCommand);
        return createAlbumLibraryResponse(events);
    }

    @PostMapping("/api/users/{userId}/album/{albumId}")
    ResponseEntity<?> insertAlbumLibrary(@PathVariable String userId, @PathVariable String albumId) {
        final InsertAlbumCommand insertAlbumCommand = new InsertAlbumCommand(new UserId(UUID.fromString(userId)), new AlbumId(UUID.fromString(albumId)));
        final List<AlbumLibraryEvent> events = albumLibraryFacade.insertAlbumLibrary(insertAlbumCommand);
        return createAlbumLibraryResponse(events);
    }

    private ResponseEntity<?> createAlbumLibraryResponse(List<AlbumLibraryEvent> events) {
        final Optional<AlbumLibraryEvent> albumLibraryEvent = events.stream().findAny();
        return albumLibraryEvent.map(found -> ResponseEntity.created(URI.create("/api/albums/" + found.aggregateId().toString())).build())
                                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
