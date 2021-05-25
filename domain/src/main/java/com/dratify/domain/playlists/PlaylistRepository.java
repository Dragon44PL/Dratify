package com.dratify.domain.playlists;

import domain.DomainRepository;
import java.util.UUID;

interface PlaylistRepository extends DomainRepository<UUID, Playlist> { }
