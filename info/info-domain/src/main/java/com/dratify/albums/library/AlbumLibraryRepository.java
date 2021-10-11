package com.dratify.albums.library;

import domain.DomainRepository;
import java.util.UUID;

interface AlbumLibraryRepository extends DomainRepository<UUID, AlbumLibrary> { }
