package com.musiva.artists.endpoint.request;

import com.musiva.artists.ArtistFacade;
import com.musiva.artists.commands.CreateArtistCommand;
import com.musiva.artists.endpoint.request.dto.CreateArtistDto;
import com.musiva.artists.event.ArtistEvent;
import com.musiva.artists.vo.ArtistType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController("/api/artist")
public class ArtistCommandEndpoint {

    private final ArtistFacade artistFacade;

    public ArtistCommandEndpoint(ArtistFacade artistFacade) {
        this.artistFacade = artistFacade;
    }

    @PostMapping
    public ResponseEntity<?> createArtist(@RequestBody CreateArtistDto createArtistDto) {
        final ArtistType artistType = ArtistType.valueOf(createArtistDto.getName());
        final CreateArtistCommand createArtistCommand = new CreateArtistCommand(createArtistDto.getName(), artistType);
        final List<ArtistEvent> events = artistFacade.createArtist(createArtistCommand);
        return createCreaetedResponse(events);
    }

    private ResponseEntity<?> createCreaetedResponse(List<ArtistEvent> artistEvents) {
        final Optional<ArtistEvent> found = artistEvents.stream().findFirst();
        return found.map(artistEvent -> ResponseEntity.created(URI.create("/api/artist/" + artistEvent.aggregateId())).build())
                    .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
