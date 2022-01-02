package com.musiva.playlists;

import com.musiva.playlists.dto.PlaylistDto;
import com.musiva.playlists.persistence.JpaPlaylistRepository;
import com.musiva.playlists.persistence.PlaylistEntity;
import com.musiva.playlists.vo.TrackId;
import com.musiva.playlists.vo.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaPlaylistQueryRepository implements PlaylistQueryRepository {

    private final JpaPlaylistRepository jpaPlaylistRepository;

    public JpaPlaylistQueryRepository(JpaPlaylistRepository jpaPlaylistRepository) {
        this.jpaPlaylistRepository = jpaPlaylistRepository;
    }

    @Override
    public Optional<PlaylistDto> findById(UUID id) {
        final Optional<PlaylistEntity> playlistEntity = jpaPlaylistRepository.findById(id);
        return playlistEntity.map(this::createPlaylistDtoFromPlaylistEntity);
    }

    @Override
    public List<PlaylistDto> findAll() {
        final List<PlaylistEntity> playlistEntity = jpaPlaylistRepository.findAll();
        return playlistEntity.stream().map(this::createPlaylistDtoFromPlaylistEntity).collect(Collectors.toList());
    }

    private PlaylistDto createPlaylistDtoFromPlaylistEntity(PlaylistEntity playlistEntity) {
        final Set<TrackId> tracks = playlistEntity.getTracks().stream().map(TrackId::new).collect(Collectors.toSet());
        final Set<UserId> collaborators = playlistEntity.getCollaborators().stream().map(UserId::new).collect(Collectors.toSet());
        return new PlaylistDto(
                playlistEntity.getId(), playlistEntity.getName(), new UserId(playlistEntity.getAuthorId()), collaborators, tracks
        );
    }
}
