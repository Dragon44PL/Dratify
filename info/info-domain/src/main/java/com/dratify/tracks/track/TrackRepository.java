package com.dratify.tracks.track;

import domain.DomainRepository;
import java.util.UUID;

interface TrackRepository extends DomainRepository<UUID, Track> { }
