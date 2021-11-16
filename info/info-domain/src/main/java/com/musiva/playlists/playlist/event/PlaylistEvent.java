package com.musiva.playlists.playlist.event;

import domain.events.DomainEvent;
import java.util.UUID;

public interface PlaylistEvent extends DomainEvent<UUID> { }
