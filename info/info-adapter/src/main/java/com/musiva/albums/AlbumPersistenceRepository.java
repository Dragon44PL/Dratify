package com.musiva.albums;

import com.musiva.albums.persistence.AlbumEntity;
import com.musiva.albums.persistence.JpaAlbumRepository;
import com.musiva.albums.persistence.handler.AlbumPersistenceHandler;
import com.musiva.albums.vo.ArtistId;
import com.musiva.albums.vo.TrackId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AlbumPersistenceRepository implements AlbumRepository {

    private final JpaAlbumRepository jpaAlbumRepository;
    private final AlbumPersistenceHandler albumPersistenceHandler;

    public AlbumPersistenceRepository(JpaAlbumRepository jpaAlbumRepository, AlbumPersistenceHandler albumPersistenceHandler) {
        this.jpaAlbumRepository = jpaAlbumRepository;
        this.albumPersistenceHandler = albumPersistenceHandler;
    }

    @Override
    public Optional<Album> findById(UUID id) {
        final Optional<AlbumEntity> albumEntity = jpaAlbumRepository.findById(id);
        return albumEntity.map(this::createAlbumFromEntity);
    }

    @Override
    public Optional<Album> findByNameAndArtist(String name, ArtistId artistId) {
        final Optional<AlbumEntity> albumEntity = jpaAlbumRepository.findByNameAndArtistId(name, artistId.id());
        return albumEntity.map(this::createAlbumFromEntity);
    }

    @Override
    public void save(Album album) {
        album.events().forEach(albumPersistenceHandler::handle);
    }

    private Album createAlbumFromEntity(AlbumEntity albumEntity) {
        return Album.restore(albumEntity.getId(), albumEntity.getName(), new ArtistId(albumEntity.getArtistId()), createTracksFromEntity(albumEntity.getTracks()));
    }

    private Set<TrackId> createTracksFromEntity(Set<UUID> tracks) {
        return tracks.stream().map(TrackId::new).collect(Collectors.toSet());
    }

}
