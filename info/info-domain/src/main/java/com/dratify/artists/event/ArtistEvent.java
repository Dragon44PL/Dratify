package com.dratify.artists.event;

import domain.events.DomainEvent;

import java.util.UUID;

public interface ArtistEvent extends DomainEvent<UUID> { }
