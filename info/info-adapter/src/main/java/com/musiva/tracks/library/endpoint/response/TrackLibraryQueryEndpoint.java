package com.musiva.tracks.library.endpoint.response;

import com.musiva.tracks.library.TrackLibraryQueryRepsoitory;
import com.musiva.tracks.library.dto.TrackLibraryDto;
import com.musiva.tracks.library.endpoint.response.dto.TrackLibraryResponse;
import com.musiva.tracks.library.vo.TrackId;
import com.musiva.tracks.library.vo.UserId;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController("/api/user/tracks")
public class TrackLibraryQueryEndpoint {

    private final TrackLibraryQueryRepsoitory trackLibraryQueryRepsoitory;

    public TrackLibraryQueryEndpoint(TrackLibraryQueryRepsoitory trackLibraryQueryRepsoitory) {
        this.trackLibraryQueryRepsoitory = trackLibraryQueryRepsoitory;
    }

    @GetMapping("/api/user/{userId}/tracks")
    ResponseEntity<?> findByUserId(@PathVariable String userId) {
        final Optional<TrackLibraryDto> trackLibrary = trackLibraryQueryRepsoitory.findByUser(new UserId(UUID.fromString(userId)));
        return trackLibrary.map(found -> ResponseEntity.ok(createTrackLibraryResponse(found))).orElseGet(() -> ResponseEntity.noContent().build());
    }

    private TrackLibraryResponse createTrackLibraryResponse(TrackLibraryDto trackLibraryDto) {
        final Set<UUID> tracks = trackLibraryDto.tracks().stream().map(TrackId::id).collect(Collectors.toSet());
        return new TrackLibraryResponse(trackLibraryDto.userId().id(), tracks);
    }
}
