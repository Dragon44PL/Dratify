package com.musiva.artists;

import com.musiva.domain.artists.event.ArtistCreatedEvent;
import com.musiva.domain.artists.event.ArtistEvent;
import com.musiva.domain.artists.vo.ArtistType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ArtistTest {

    private final UUID ARTIST_ID = UUID.randomUUID();
    private final String ARTIST_NAME = "Artist";
    private final ArtistType ARTIST_TYPE = ArtistType.ARTIST;

    /**
     *  Artist Events
     */

    private final Class<ArtistCreatedEvent> ARTIST_CREATED_EVENT = ArtistCreatedEvent.class;

    @Test
    @DisplayName("Artist Should Create Properly And Generate ArtistCreatedEvent")
    void artistShouldCreateProperlyAndGenerateEvent() {
        final Artist artist = Artist.create(ARTIST_ID, ARTIST_NAME, ARTIST_TYPE);

        Optional<ArtistEvent> artistEvent = artist.findLatestEvent();
        Assertions.assertTrue(artistEvent.isPresent());
        assertEquals(ARTIST_CREATED_EVENT, artistEvent.get().getClass());

        final ArtistCreatedEvent artistCreatedEvent = (ArtistCreatedEvent) artistEvent.get();
        assertEquals(ARTIST_ID, artistCreatedEvent.aggregateId());
        assertEquals(ARTIST_NAME, artistCreatedEvent.name());
        assertEquals(ARTIST_TYPE, artistCreatedEvent.artistType());
    }

    @Test
    @DisplayName("Artist Should Restore Properly And Not Generate Event")
    void artistShouldRestoreProperlyAndNotGenerateEvent() {
        final Artist artist = Artist.restore(ARTIST_ID, ARTIST_NAME, ARTIST_TYPE);

        Optional<ArtistEvent> artistEvent = artist.findLatestEvent();
        Assertions.assertFalse(artistEvent.isPresent());
    }
}
