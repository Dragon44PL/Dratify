package com.musiva.tracks.track.endpoint.response;

import com.musiva.tracks.track.TrackQueryRepository;

import com.musiva.tracks.track.dto.TrackDto;
import com.musiva.tracks.track.endpoint.response.dto.TrackResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController("/api/tracks")
class TrackQueryEndpoint {

    private final TrackQueryRepository trackQueryRepository;

    TrackQueryEndpoint(TrackQueryRepository trackQueryRepository) {
        this.trackQueryRepository = trackQueryRepository;
    }

    @GetMapping
    List<TrackResponse> findAllTracks() {
        final List<TrackDto> tracks = trackQueryRepository.findAll();
        return tracks.stream().map(this::createTrackResponse).collect(Collectors.toList());
    }

    private TrackResponse createTrackResponse(TrackDto trackDto) {
        return new TrackResponse(trackDto.id().toString(), trackDto.name(), trackDto.counter());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable String id) {
        final UUID uuid = UUID.fromString(id);
        final Optional<TrackDto> trackDto = trackQueryRepository.findById(uuid);
        return trackDto.isPresent() ? ResponseEntity.ok(createTrackResponse(trackDto.get())) : ResponseEntity.noContent().build();
    }
}
