package com.musiva.albums.library;

import com.musiva.albums.library.dto.AlbumLibraryDto;
import com.musiva.albums.library.persistence.AlbumLibraryEntity;
import com.musiva.albums.library.persistence.JpaAlbumLibraryEntityRepository;
import com.musiva.albums.library.vo.AlbumId;
import com.musiva.albums.library.vo.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaAlbumLibraryQueryRepository implements AlbumLibraryQueryRepository {

    final JpaAlbumLibraryEntityRepository jpaAlbumLibraryEntityRepository;

    public JpaAlbumLibraryQueryRepository(JpaAlbumLibraryEntityRepository jpaAlbumLibraryEntityRepository) {
        this.jpaAlbumLibraryEntityRepository = jpaAlbumLibraryEntityRepository;
    }

    @Override
    public Optional<AlbumLibraryDto> findById(UUID id) {
        final Optional<AlbumLibraryEntity> albumLibraryEntity = jpaAlbumLibraryEntityRepository.findById(id);
        return albumLibraryEntity.map(this::createAlbumLibraryFromEntity);
    }

    @Override
    public Optional<AlbumLibraryDto> findByUser(UserId userId) {
        final Optional<AlbumLibraryEntity> albumLibraryEntity = jpaAlbumLibraryEntityRepository.findByUserId(userId.id());
        return albumLibraryEntity.map(this::createAlbumLibraryFromEntity);
    }

    @Override
    public List<AlbumLibraryDto> findAll() {
        final List<AlbumLibraryEntity> albumLibraryEntity = jpaAlbumLibraryEntityRepository.findAll();
        return albumLibraryEntity.stream().map(this::createAlbumLibraryFromEntity).collect(Collectors.toList());
    }

    private AlbumLibraryDto createAlbumLibraryFromEntity(AlbumLibraryEntity albumLibraryEntity) {
        final Set<AlbumId> albums = albumLibraryEntity.getAlbums().stream().map(AlbumId::new).collect(Collectors.toSet());
        return new AlbumLibraryDto(albumLibraryEntity.getId(), new UserId(albumLibraryEntity.getUserId()), albums);
    }
}
