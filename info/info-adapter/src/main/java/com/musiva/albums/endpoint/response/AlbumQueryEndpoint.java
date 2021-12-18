package com.musiva.albums.endpoint.response;

import com.musiva.albums.AlbumQueryRepository;
import com.musiva.albums.dto.AlbumDto;
import com.musiva.albums.endpoint.response.dto.AlbumResponseDto;
import com.musiva.albums.vo.TrackId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController("/api/album")
public class AlbumQueryEndpoint {

    private final AlbumQueryRepository albumQueryRepository;

    public AlbumQueryEndpoint(AlbumQueryRepository albumQueryRepository) {
        this.albumQueryRepository = albumQueryRepository;
    }

    @GetMapping
    List<AlbumResponseDto> findAllAlbums() {
        final List<AlbumDto> albums = albumQueryRepository.findAll();
        return albums.stream().map(this::createAlbumResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable String id) {
        final Optional<AlbumDto> album = albumQueryRepository.findById(UUID.fromString(id));
        return album.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    private AlbumResponseDto createAlbumResponse(AlbumDto albumDto) {
        final Set<UUID> tracks = albumDto.tracks().stream().map(TrackId::id).collect(Collectors.toSet());
        return new AlbumResponseDto(albumDto.id(), albumDto.name(), albumDto.artist().id(), tracks);
    }

}
