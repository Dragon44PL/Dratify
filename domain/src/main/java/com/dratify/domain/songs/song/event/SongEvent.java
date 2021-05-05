package com.dratify.domain.songs.song.event;

import domain.events.DomainEvent;
import java.util.UUID;

public interface SongEvent extends DomainEvent<UUID> { }
