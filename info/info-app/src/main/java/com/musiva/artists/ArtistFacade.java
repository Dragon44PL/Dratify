package com.musiva.artists;

import com.musiva.artists.commands.CreateArtistCommand;
import com.musiva.artists.event.ArtistEvent;
import com.musiva.artists.exception.ArtistAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArtistFacade {

    private final ArtistRepository artistRepository;

    public ArtistFacade(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<ArtistEvent> createArtist(CreateArtistCommand createArtistCommand) {
        final Optional<Artist> artist = artistRepository.findByName(createArtistCommand.name());

        if(artist.isPresent()) {
            throw new ArtistAlreadyExistsException("Artist with name : '" + createArtistCommand.name() + "' already created");
        }

        final UUID randomId = UUID.randomUUID();
        final Artist created = Artist.create(randomId, createArtistCommand.name(), createArtistCommand.artistType());
        artistRepository.save(created);
        return created.events();
    }

}
