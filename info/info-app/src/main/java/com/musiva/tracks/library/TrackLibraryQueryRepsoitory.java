package com.musiva.tracks.library;

import com.musiva.tracks.library.dto.TrackLibraryDto;
import com.musiva.tracks.library.vo.UserId;

import java.util.Optional;
import java.util.UUID;

public interface TrackLibraryQueryRepsoitory {
    Optional<TrackLibraryDto> findByUser(UserId userId);
    Optional<TrackLibraryDto> findById(UUID id);
}
