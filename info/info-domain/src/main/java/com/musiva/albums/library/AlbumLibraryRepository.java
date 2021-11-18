package com.musiva.albums.library;

import com.musiva.albums.library.vo.UserId;
import domain.DomainRepository;

import java.util.Optional;
import java.util.UUID;

interface AlbumLibraryRepository extends DomainRepository<UUID, AlbumLibrary> {
    Optional<AlbumLibrary> findByUser(UserId userId);
}
