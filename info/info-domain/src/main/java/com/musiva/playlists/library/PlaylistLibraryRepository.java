package com.musiva.playlists.library;

import domain.DomainRepository;

import java.util.UUID;

interface PlaylistLibraryRepository extends DomainRepository<UUID, PlaylistLibrary> { }
