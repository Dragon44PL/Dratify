package com.musiva.albums.album;

import domain.DomainRepository;
import java.util.UUID;

interface AlbumRepository extends DomainRepository<UUID, Album> { }
