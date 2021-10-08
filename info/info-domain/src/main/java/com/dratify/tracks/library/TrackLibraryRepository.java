package com.dratify.tracks.library;

import domain.DomainRepository;
import java.util.UUID;

interface TrackLibraryRepository extends DomainRepository<UUID, TrackLibrary> { }
