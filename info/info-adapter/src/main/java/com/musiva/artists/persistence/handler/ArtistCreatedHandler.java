package com.musiva.artists.persistence.handler;

import com.musiva.artists.event.ArtistCreatedEvent;
import com.musiva.artists.persistence.ArtistEntity;
import com.musiva.artists.persistence.ArtistTypeEntity;
import com.musiva.artists.persistence.JpaArtistRepository;
import domain.events.DomainEventHandler;

public class ArtistCreatedHandler implements DomainEventHandler<ArtistCreatedEvent> {

    private final JpaArtistRepository jpaArtistRepository;

    public ArtistCreatedHandler(JpaArtistRepository jpaArtistRepository) {
        this.jpaArtistRepository = jpaArtistRepository;
    }

    @Override
    public void handle(ArtistCreatedEvent artistCreatedEvent) {
        final ArtistTypeEntity artistTypeEntity = new ArtistTypeEntity();
        artistTypeEntity.setType(artistCreatedEvent.artistType());

        final ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setId(artistCreatedEvent.aggregateId());
        artistEntity.setName(artistCreatedEvent.name());
        artistEntity.setArtistTypeEntity(artistTypeEntity);

        jpaArtistRepository.save(artistEntity);
    }
}
