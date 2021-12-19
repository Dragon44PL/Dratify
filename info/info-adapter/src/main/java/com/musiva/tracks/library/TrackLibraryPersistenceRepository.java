package com.musiva.tracks.library;

import com.musiva.tracks.library.persistence.JpaTrackLibraryRepository;
import com.musiva.tracks.library.persistence.TrackLibraryEntity;
import com.musiva.tracks.library.persistence.handler.TrackLibraryPersistenceHandler;
import com.musiva.tracks.library.vo.TrackId;
import com.musiva.tracks.library.vo.UserId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
class TrackLibraryPersistenceRepository implements TrackLibraryRepository {

    private final TrackLibraryPersistenceHandler trackLibraryPersistenceHandler;
    private final JpaTrackLibraryRepository jpaTrackLibraryRepository;

    public TrackLibraryPersistenceRepository(TrackLibraryPersistenceHandler trackLibraryPersistenceHandler, JpaTrackLibraryRepository jpaTrackLibraryRepository) {
        this.trackLibraryPersistenceHandler = trackLibraryPersistenceHandler;
        this.jpaTrackLibraryRepository = jpaTrackLibraryRepository;
    }

    @Override
    public Optional<TrackLibrary> findByUserId(UserId userId) {
        final Optional<TrackLibraryEntity> trackLibrary = jpaTrackLibraryRepository.findByUserId(userId.id());
        return trackLibrary.map(this::createTrackLibraryFromEntity);
    }

    @Override
    public Optional<TrackLibrary> findById(UUID id) {
        final Optional<TrackLibraryEntity> trackLibrary = jpaTrackLibraryRepository.findById(id);
        return trackLibrary.map(this::createTrackLibraryFromEntity);
    }

    @Override
    public void save(TrackLibrary trackLibrary) {
        trackLibrary.events().forEach(trackLibraryPersistenceHandler::handle);
    }

    private TrackLibrary createTrackLibraryFromEntity(TrackLibraryEntity trackLibraryEntity) {
        final Set<TrackId> tracks = trackLibraryEntity.getTracks().stream().map(TrackId::new).collect(Collectors.toSet());
        return TrackLibrary.restore(trackLibraryEntity.getId(), new UserId(trackLibraryEntity.getUserId()), tracks);
    }
}
