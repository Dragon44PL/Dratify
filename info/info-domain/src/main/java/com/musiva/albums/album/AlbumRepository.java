package com.musiva.albums.album;

import com.musiva.albums.album.vo.ArtistId;
import domain.DomainRepository;

import java.util.Optional;
import java.util.UUID;

interface AlbumRepository extends DomainRepository<UUID, Album> {
    Optional<Album> findByName(String name);
    Optional<Album> findByArtist(ArtistId artistId);
}
