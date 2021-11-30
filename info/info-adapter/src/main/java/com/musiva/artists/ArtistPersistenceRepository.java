package com.musiva.artists;

import com.musiva.artists.persistence.ArtistEntity;
import com.musiva.artists.persistence.JpaArtistRepository;
import com.musiva.artists.persistence.handler.ArtistPersistenceHandler;

import java.util.Optional;
import java.util.UUID;

public class ArtistPersistenceRepository implements ArtistRepository {

    private final ArtistPersistenceHandler artistPersistenceHandler;
    private final JpaArtistRepository jpaArtistRepository;

    public ArtistPersistenceRepository(ArtistPersistenceHandler artistPersistenceHandler, JpaArtistRepository jpaArtistRepository) {
        this.artistPersistenceHandler = artistPersistenceHandler;
        this.jpaArtistRepository = jpaArtistRepository;
    }

    @Override
    public Optional<Artist> findByName(String name) {
        final Optional<ArtistEntity> artistEntity = jpaArtistRepository.findByName(name);
        return artistEntity.map(this::convertEntity);
    }

    @Override
    public Optional<Artist> findById(UUID id) {
        final Optional<ArtistEntity> artistEntity = jpaArtistRepository.findById(id);
        return artistEntity.map(this::convertEntity);
    }

    @Override
    public void save(Artist artist) {
        artist.events().forEach(artistPersistenceHandler::handle);
    }

    private Artist convertEntity(ArtistEntity artistEntity) {
        return Artist.restore(artistEntity.getId(), artistEntity.getName(), artistEntity.getArtistTypeEntity().getType());
    }
}
