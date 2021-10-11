package com.dratify.albums.library.event;

import domain.events.DomainEvent;
import java.util.UUID;

public interface AlbumLibraryEvent extends DomainEvent<UUID> { }
