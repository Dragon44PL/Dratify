package com.musiva.tracks.track;

import com.musiva.tracks.track.dto.TrackDto;
import com.musiva.tracks.track.persistence.JpaTrackRepository;
import com.musiva.tracks.track.persistence.TrackEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaTrackQueryRepository implements TrackQueryRepository {

    private final JpaTrackRepository jpaTrackRepository;

    public JpaTrackQueryRepository(JpaTrackRepository jpaTrackRepository) {
        this.jpaTrackRepository = jpaTrackRepository;
    }

    @Override
    public Optional<TrackDto> findById(UUID id) {
        final Optional<TrackEntity> trackEntity = jpaTrackRepository.findById(id);
        return trackEntity.map(this::createTrackDtoFromEntity);
    }

    @Override
    public Optional<TrackDto> findByName(String name) {
        final Optional<TrackEntity> trackEntity = jpaTrackRepository.findByName(name);
        return trackEntity.map(this::createTrackDtoFromEntity);
    }

    @Override
    public List<TrackDto> findAll() {
        final List<TrackEntity> trackEntity = jpaTrackRepository.findAll();
        return trackEntity.stream().map(this::createTrackDtoFromEntity).collect(Collectors.toList());
    }

    private TrackDto createTrackDtoFromEntity(TrackEntity trackEntity) {
        return new TrackDto(trackEntity.getId(), trackEntity.getName(), trackEntity.getTrackCounterEntity().getCounter());
    }
}
