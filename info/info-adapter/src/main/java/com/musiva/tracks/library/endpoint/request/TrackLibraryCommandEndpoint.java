package com.musiva.tracks.library.endpoint.request;

import com.musiva.tracks.library.TrackLibraryFacade;
import com.musiva.tracks.library.commands.CreateTrackLibraryCommand;
import com.musiva.tracks.library.commands.InsertTrackCommand;
import com.musiva.tracks.library.event.TrackLibraryEvent;
import com.musiva.tracks.library.vo.TrackId;
import com.musiva.tracks.library.vo.UserId;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/api/user/tracks")
public class TrackLibraryCommandEndpoint {

    private final TrackLibraryFacade trackLibraryFacade;

    public TrackLibraryCommandEndpoint(TrackLibraryFacade trackLibraryFacade) {
        this.trackLibraryFacade = trackLibraryFacade;
    }

    @PostMapping("/api/user/{userId}/tracks")
    ResponseEntity<?> createTrackLibrary(@PathVariable UUID userId) {
        final CreateTrackLibraryCommand createTrackLibraryCommand = new CreateTrackLibraryCommand(new UserId(userId));
        final List<TrackLibraryEvent> events = trackLibraryFacade.createTrackLibrary(createTrackLibraryCommand);
        return createTrackLibraryResponse(events);
    }

    @PostMapping("/api/user/{userId}/tracks/{trackId}")
    ResponseEntity<?> insertTrack(@PathVariable String userId, @PathVariable String trackId) {
        final InsertTrackCommand insertTrackCommand = new InsertTrackCommand(UUID.fromString(userId), new TrackId(UUID.fromString(trackId)));
        final List<TrackLibraryEvent> events = trackLibraryFacade.insertTrack(insertTrackCommand);
        return createTrackLibraryResponse(events);
    }

    private ResponseEntity<?> createTrackLibraryResponse(List<TrackLibraryEvent> events) {
        final Optional<TrackLibraryEvent> trackLibraryEvent = events.stream().findAny();
        return trackLibraryEvent
                .map(libraryEvent -> ResponseEntity.created(URI.create("/api/user/tracks/" + libraryEvent.aggregateId())).build())
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }



}
