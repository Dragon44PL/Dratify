package com.dratify.albums.album.event;

import domain.events.DomainEvent;
import java.util.UUID;

public interface AlbumEvent extends DomainEvent<UUID> { }
