package com.musiva.playlists.playlist;

import com.musiva.playlists.playlist.persistence.JpaPlaylistRepository;
import com.musiva.playlists.playlist.persistence.PlaylistEntity;
import com.musiva.playlists.playlist.persistence.handler.PlaylistPersistenceHandler;
import com.musiva.playlists.playlist.vo.TrackId;
import com.musiva.playlists.playlist.vo.UserId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PlaylistPersistenceRepository implements PlaylistRepository {

    private final PlaylistPersistenceHandler playlistPersistenceHandler;
    private final JpaPlaylistRepository jpaPlaylistRepository;

    public PlaylistPersistenceRepository(PlaylistPersistenceHandler playlistPersistenceHandler, JpaPlaylistRepository jpaPlaylistRepository) {
        this.playlistPersistenceHandler = playlistPersistenceHandler;
        this.jpaPlaylistRepository = jpaPlaylistRepository;
    }

    @Override
    public Optional<Playlist> findByName(String name) {
        final Optional<PlaylistEntity> playlistEntity = jpaPlaylistRepository.findByName(name);
        return playlistEntity.map(this::createPlaylistFromEntity);
    }

    @Override
    public Optional<Playlist> findById(UUID id) {
        final Optional<PlaylistEntity> playlistEntity = jpaPlaylistRepository.findById(id);
        return playlistEntity.map(this::createPlaylistFromEntity);
    }

    @Override
    public void save(Playlist playlist) {
        playlist.events().forEach(playlistPersistenceHandler::handle);
    }

    private Playlist createPlaylistFromEntity(PlaylistEntity playlistEntity) {
        final Set<UserId> collaborators = playlistEntity.getCollaborators().stream().map(UserId::new).collect(Collectors.toSet());
        final Set<TrackId> tracks = playlistEntity.getTracks().stream().map(TrackId::new).collect(Collectors.toSet());
        return Playlist.restore(playlistEntity.getId(), playlistEntity.getName(), new UserId(playlistEntity.getAuthorId()), collaborators, tracks);
    }
}
