package com.musiva.playlists.event;

import domain.events.DomainEvent;
import java.util.UUID;

public interface PlaylistEvent extends DomainEvent<UUID> { }
