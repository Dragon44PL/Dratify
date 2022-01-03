package com.musiva.albums.album;

import com.musiva.albums.album.dto.AlbumDto;
import com.musiva.albums.album.persistence.AlbumEntity;
import com.musiva.albums.album.persistence.JpaAlbumRepository;
import com.musiva.albums.album.vo.ArtistId;
import com.musiva.albums.album.vo.TrackId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaAlbumQueryRepository implements AlbumQueryRepository {

    private final JpaAlbumRepository jpaAlbumRepository;

    public JpaAlbumQueryRepository(JpaAlbumRepository jpaAlbumRepository) {
        this.jpaAlbumRepository = jpaAlbumRepository;
    }

    @Override
    public Optional<AlbumDto> findById(UUID id) {
        final Optional<AlbumEntity> albumEntity = jpaAlbumRepository.findById(id);
        return albumEntity.map(this::createAlbumDtoFromEntity);
    }

    @Override
    public List<AlbumDto> findAll() {
        final List<AlbumEntity> albumEntity = jpaAlbumRepository.findAll();
        return albumEntity.stream().map(this::createAlbumDtoFromEntity).collect(Collectors.toList());
    }

    private AlbumDto createAlbumDtoFromEntity(AlbumEntity albumEntity) {
        final Set<TrackId> tracks = albumEntity.getTracks().stream().map(TrackId::new).collect(Collectors.toSet());
        return new AlbumDto(albumEntity.getId(), albumEntity.getName(), tracks, new ArtistId(albumEntity.getArtistId()));
    }
}
