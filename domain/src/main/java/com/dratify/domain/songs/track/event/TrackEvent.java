package com.dratify.domain.songs.track.event;

import domain.events.DomainEvent;
import java.util.UUID;

public interface TrackEvent extends DomainEvent<UUID> { }
