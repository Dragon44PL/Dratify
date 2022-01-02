package com.musiva.tracks.library;

import com.musiva.tracks.library.dto.TrackLibraryDto;
import com.musiva.tracks.library.persistence.JpaTrackLibraryRepository;
import com.musiva.tracks.library.persistence.TrackLibraryEntity;
import com.musiva.tracks.library.vo.TrackId;
import com.musiva.tracks.library.vo.UserId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaTrackLibraryQueryRepository implements TrackLibraryQueryRepsoitory {

    private final JpaTrackLibraryRepository jpaTrackLibraryRepository;

    public JpaTrackLibraryQueryRepository(JpaTrackLibraryRepository jpaTrackLibraryRepository) {
        this.jpaTrackLibraryRepository = jpaTrackLibraryRepository;
    }

    @Override
    public Optional<TrackLibraryDto> findByUser(UserId userId) {
        final Optional<TrackLibraryEntity> trackLibraryEntity = jpaTrackLibraryRepository.findByUserId(userId.id());
        return trackLibraryEntity.map(this::createTrackLibraryDtoFromEntity);
    }

    @Override
    public Optional<TrackLibraryDto> findById(UUID id) {
        final Optional<TrackLibraryEntity> trackLibraryEntity = jpaTrackLibraryRepository.findById(id);
        return trackLibraryEntity.map(this::createTrackLibraryDtoFromEntity);
    }

    private TrackLibraryDto createTrackLibraryDtoFromEntity(TrackLibraryEntity trackLibraryEntity) {
        final Set<TrackId> tracks = trackLibraryEntity.getTracks().stream().map(TrackId::new).collect(Collectors.toSet());
        return new TrackLibraryDto(trackLibraryEntity.getId(), new UserId(trackLibraryEntity.getUserId()), tracks);
    }
}
