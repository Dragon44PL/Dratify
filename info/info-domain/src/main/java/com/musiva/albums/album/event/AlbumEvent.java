package com.musiva.albums.album.event;

import domain.events.DomainEvent;
import java.util.UUID;

public interface AlbumEvent extends DomainEvent<UUID> { }
