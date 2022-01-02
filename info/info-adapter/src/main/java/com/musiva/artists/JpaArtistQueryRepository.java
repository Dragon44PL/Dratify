package com.musiva.artists;

import com.musiva.artists.dto.ArtistDto;
import com.musiva.artists.persistence.ArtistEntity;
import com.musiva.artists.persistence.JpaArtistRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaArtistQueryRepository implements ArtistQueryRepository {

    private final JpaArtistRepository jpaArtistRepository;

    public JpaArtistQueryRepository(JpaArtistRepository jpaArtistRepository) {
        this.jpaArtistRepository = jpaArtistRepository;
    }

    @Override
    public Optional<ArtistDto> findById(UUID id) {
        final Optional<ArtistEntity> artistEntity = jpaArtistRepository.findById(id);
        return artistEntity.map(this::createArtistDtoFromEntity);
    }

    @Override
    public Optional<ArtistDto> findByName(String name) {
        final Optional<ArtistEntity> artistEntity = jpaArtistRepository.findByName(name);
        return artistEntity.map(this::createArtistDtoFromEntity);
    }

    @Override
    public List<ArtistDto> findAll() {
        final List<ArtistEntity> artistEntities = jpaArtistRepository.findAll();
        return artistEntities.stream().map(this::createArtistDtoFromEntity).collect(Collectors.toList());
    }

    private ArtistDto createArtistDtoFromEntity(ArtistEntity artistEntity) {
        return new ArtistDto(artistEntity.getId(), artistEntity.getName(), artistEntity.getArtistTypeEntity().getType());
    }
}
