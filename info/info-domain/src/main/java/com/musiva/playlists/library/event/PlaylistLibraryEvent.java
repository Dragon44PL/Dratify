package com.musiva.playlists.library.event;

import domain.events.DomainEvent;
import java.util.UUID;

public interface PlaylistLibraryEvent extends DomainEvent<UUID> { }
