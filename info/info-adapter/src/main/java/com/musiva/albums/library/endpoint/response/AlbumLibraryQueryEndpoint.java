package com.musiva.albums.library.endpoint.response;

import com.musiva.albums.library.AlbumLibraryQueryRepository;
import com.musiva.albums.library.dto.AlbumLibraryDto;
import com.musiva.albums.library.endpoint.response.dto.AlbumLibraryResponse;
import com.musiva.albums.library.vo.AlbumId;
import com.musiva.albums.library.vo.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AlbumLibraryQueryEndpoint {

    private final AlbumLibraryQueryRepository albumLibraryQueryRepository;

    public AlbumLibraryQueryEndpoint(AlbumLibraryQueryRepository albumLibraryQueryRepository) {
        this.albumLibraryQueryRepository = albumLibraryQueryRepository;
    }

    @GetMapping("/api/users/{userId}/album")
    ResponseEntity<?> findAlbumLibraryById(@PathVariable String userId) {
        final Optional<AlbumLibraryDto> albumLibraryDto = albumLibraryQueryRepository.findByUser(new UserId(UUID.fromString(userId)));
        return albumLibraryDto.map(found -> ResponseEntity.ok(createAlbumLibraryResponse(found))).orElseGet(() -> ResponseEntity.noContent().build());
    }

    private AlbumLibraryResponse createAlbumLibraryResponse(AlbumLibraryDto albumLibraryDto) {
        final Set<UUID> albums = albumLibraryDto.albums().stream().map(AlbumId::id).collect(Collectors.toSet());
        return new AlbumLibraryResponse(albumLibraryDto.id(), albumLibraryDto.userId().id(), albums);
    }
}
