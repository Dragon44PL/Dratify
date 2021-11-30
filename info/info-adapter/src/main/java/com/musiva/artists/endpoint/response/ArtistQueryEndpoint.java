package com.musiva.artists.endpoint.response;

import com.musiva.artists.ArtistQueryRepository;
import com.musiva.artists.dto.ArtistDto;
import com.musiva.artists.endpoint.response.dto.ArtistResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController("/api/artist")
public class ArtistQueryEndpoint {

    private final ArtistQueryRepository artistQueryRepository;

    public ArtistQueryEndpoint(ArtistQueryRepository artistQueryRepository) {
        this.artistQueryRepository = artistQueryRepository;
    }

    @GetMapping
    List<ArtistResponseDto> findAllArtist() {
        final List<ArtistDto> artists = artistQueryRepository.findAll();
        return artists.stream().map(this::createArtistResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findArtistById(@PathVariable String id) {
        final Optional<ArtistDto> artist = artistQueryRepository.findById(UUID.fromString(id));
        return artist.isPresent() ? ResponseEntity.ok(artist.get()) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}")
    ResponseEntity<?> findArtistByName(@PathVariable String name) {
        final Optional<ArtistDto> artist = artistQueryRepository.findByName(name);
        return artist.isPresent() ? ResponseEntity.ok(artist.get()) : ResponseEntity.noContent().build();
    }

    private ArtistResponseDto createArtistResponse(ArtistDto artistDto) {
        return new ArtistResponseDto(artistDto.id(), artistDto.name(), artistDto.artistType().name());
    }
}
