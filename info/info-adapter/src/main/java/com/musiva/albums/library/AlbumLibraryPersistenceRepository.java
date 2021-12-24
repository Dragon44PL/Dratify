package com.musiva.albums.library;

import com.musiva.albums.library.persistence.AlbumLibraryEntity;
import com.musiva.albums.library.persistence.JpaAlbumLibraryEntityRepository;
import com.musiva.albums.library.persistence.handler.AlbumLibraryPersistenceHandler;
import com.musiva.albums.library.vo.AlbumId;
import com.musiva.albums.library.vo.UserId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AlbumLibraryPersistenceRepository implements AlbumLibraryRepository {

    private final AlbumLibraryPersistenceHandler albumLibraryPersistenceHandler;
    private final JpaAlbumLibraryEntityRepository albumLibraryEntityRepository;

    public AlbumLibraryPersistenceRepository(AlbumLibraryPersistenceHandler albumLibraryPersistenceHandler, JpaAlbumLibraryEntityRepository albumLibraryEntityRepository) {
        this.albumLibraryPersistenceHandler = albumLibraryPersistenceHandler;
        this.albumLibraryEntityRepository = albumLibraryEntityRepository;
    }

    @Override
    public Optional<AlbumLibrary> findByUser(UserId userId) {
        final Optional<AlbumLibraryEntity> albumLibraryEntity = albumLibraryEntityRepository.findByUserId(userId.id());
        return albumLibraryEntity.map(this::createAlbumLibraryFromEntity);
    }

    @Override
    public Optional<AlbumLibrary> findById(UUID id) {
        final Optional<AlbumLibraryEntity> albumLibraryEntity = albumLibraryEntityRepository.findById(id);
        return albumLibraryEntity.map(this::createAlbumLibraryFromEntity);
    }

    @Override
    public void save(AlbumLibrary albumLibrary) {
        albumLibrary.events().forEach(albumLibraryPersistenceHandler::handle);
    }

    private AlbumLibrary createAlbumLibraryFromEntity(AlbumLibraryEntity albumLibraryEntity) {
        final Set<AlbumId> albums = albumLibraryEntity.getAlbums().stream().map(AlbumId::new).collect(Collectors.toSet());
        return AlbumLibrary.restore(albumLibraryEntity.getId(), new UserId(albumLibraryEntity.getUserId()), albums);
    }
}
