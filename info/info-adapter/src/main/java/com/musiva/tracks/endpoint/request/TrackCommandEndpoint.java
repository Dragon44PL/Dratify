package com.musiva.tracks.endpoint.request;

import com.musiva.tracks.TrackFacade;
import com.musiva.tracks.commands.CreateTrackCommand;
import com.musiva.tracks.commands.IncrementCounterCommand;
import com.musiva.tracks.endpoint.request.dto.CreateTrackRequest;
import com.musiva.tracks.event.TrackEvent;
import com.musiva.tracks.endpoint.request.dto.CreateTrackDataPathRequest;
import com.musiva.tracks.vo.TrackDataPath;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller("/api/tracks")
class TrackCommandEndpoint {

    private final TrackFacade trackFacade;

    TrackCommandEndpoint(TrackFacade trackFacade) {
        this.trackFacade = trackFacade;
    }

    @PostMapping
    ResponseEntity<?> createTrack(@RequestBody CreateTrackRequest createTrackRequest) {
        final CreateTrackCommand createTrackCommand = createTrackCommand(createTrackRequest);
        final List<TrackEvent> events = trackFacade.createTrack(createTrackCommand);
        return !events.isEmpty() ? returnCreatedTrackIdPath(events.stream().findFirst().get()) : ResponseEntity.badRequest().build();
    }

    private CreateTrackCommand createTrackCommand(CreateTrackRequest createTrackRequest) {
        final CreateTrackDataPathRequest createTrackDataPathRequest = createTrackRequest.getTrackDataPath();
        final TrackDataPath trackDataPath = new TrackDataPath(createTrackDataPathRequest.getPath(), createTrackDataPathRequest.getFilename(), createTrackDataPathRequest.getExtension());
        return new CreateTrackCommand(createTrackRequest.getName(), trackDataPath);
    }

    private ResponseEntity<?> returnCreatedTrackIdPath(TrackEvent trackEvent) {
        return ResponseEntity.created(URI.create("/api/tracks/" + trackEvent.aggregateId())).build();
    }

    @PatchMapping("/api/tracks/{id}/listened")
    ResponseEntity<?> listenTrack(@PathVariable String id) {
        final IncrementCounterCommand incrementCounterCommand = incrementCounterCommand(id);
        final List<TrackEvent> events = trackFacade.incrementCounter(incrementCounterCommand);
        return !events.isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    private IncrementCounterCommand incrementCounterCommand(String id) {
        return new IncrementCounterCommand(UUID.fromString(id));
    }

}
