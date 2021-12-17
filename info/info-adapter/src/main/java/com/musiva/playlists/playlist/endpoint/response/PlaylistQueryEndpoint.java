package com.musiva.playlists.playlist.endpoint.response;

import com.musiva.playlists.playlist.PlaylistQueryRepository;
import com.musiva.playlists.playlist.dto.PlaylistDto;
import com.musiva.playlists.playlist.endpoint.response.dto.PlaylistResponse;
import com.musiva.playlists.playlist.vo.TrackId;
import com.musiva.playlists.playlist.vo.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController("/api/playlist")
public class PlaylistQueryEndpoint {

    private final PlaylistQueryRepository playlistQueryRepository;

    public PlaylistQueryEndpoint(PlaylistQueryRepository playlistQueryRepository) {
        this.playlistQueryRepository = playlistQueryRepository;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable String id) {
        Optional<PlaylistDto> playlistDto = playlistQueryRepository.findById(UUID.fromString(id));
        return playlistDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping
    List<PlaylistResponse> findAll() {
        return playlistQueryRepository.findAll().stream().map(this::createPlaylistResponse).collect(Collectors.toList());
    }

    private PlaylistResponse createPlaylistResponse(PlaylistDto playlistDto) {
        final Set<UUID> collaborators = playlistDto.collaborators().stream().map(UserId::id).collect(Collectors.toSet());
        final Set<UUID> tracks = playlistDto.tracks().stream().map(TrackId::id).collect(Collectors.toSet());
        return new PlaylistResponse(playlistDto.id(), playlistDto.name(), playlistDto.author().id(), collaborators, tracks);
    }
}
