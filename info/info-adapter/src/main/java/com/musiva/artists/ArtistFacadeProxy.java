package com.musiva.artists;

import com.musiva.artists.commands.CreateArtistCommand;
import com.musiva.artists.event.ArtistEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistFacadeProxy extends ArtistFacade {

    public ArtistFacadeProxy(ArtistRepository artistRepository) {
        super(artistRepository);
    }

    @Override
    @Transactional
    public List<ArtistEvent> createArtist(CreateArtistCommand createArtistCommand) {
        return super.createArtist(createArtistCommand);
    }
}
