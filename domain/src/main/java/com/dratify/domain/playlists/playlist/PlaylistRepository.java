package com.dratify.domain.playlists.playlist;

import domain.DomainRepository;
import java.util.UUID;

interface PlaylistRepository extends DomainRepository<UUID, Playlist> { }
