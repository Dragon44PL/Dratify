package com.musiva.albums.album;

import com.musiva.albums.album.vo.ArtistId;
import domain.DomainRepository;

import java.util.Optional;
import java.util.UUID;

interface AlbumRepository extends DomainRepository<UUID, Album> {
    Optional<Album> findByNameAndArtist(String name, ArtistId artistId);
}
