package com.musiva.tracks.library.dto;

import com.musiva.tracks.library.vo.TrackId;
import com.musiva.tracks.library.vo.UserId;

import java.util.Set;
import java.util.UUID;

public record TrackLibraryDto(UUID id, UserId userId, Set<TrackId> tracks) { }
