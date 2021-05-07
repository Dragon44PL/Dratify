package com.dratify.domain.songs.library.event;

import domain.events.DomainEvent;
import java.util.UUID;

public interface TrackLibraryEvent extends DomainEvent<UUID> {}
