package com.dratify.domain.albums.album;

import domain.DomainRepository;
import java.util.UUID;

interface AlbumRepository extends DomainRepository<UUID, Album> { }
