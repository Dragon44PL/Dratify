package com.musiva.tracks;

import com.musiva.tracks.persistence.TrackDataEntity;
import com.musiva.tracks.persistence.JpaTrackRepository;
import com.musiva.tracks.persistence.TrackEntity;
import com.musiva.tracks.persistence.handler.TrackPersistenceHandler;
import com.musiva.tracks.vo.ListeningCounter;
import com.musiva.tracks.vo.TrackDataPath;

import java.util.Optional;
import java.util.UUID;

public class TrackPersistenceRepository implements TrackRepository {

    private final TrackPersistenceHandler trackPersistenceHandler;
    private final JpaTrackRepository jpaTrackRepository;

    public TrackPersistenceRepository(TrackPersistenceHandler trackPersistenceHandler, JpaTrackRepository jpaTrackRepository) {
        this.trackPersistenceHandler = trackPersistenceHandler;
        this.jpaTrackRepository = jpaTrackRepository;
    }

    @Override
    public Optional<Track> findByName(String name) {
        final Optional<TrackEntity> track = jpaTrackRepository.findByName(name);
        return track.map(this::track);
    }

    @Override
    public Optional<Track> findById(UUID id) {
        final Optional<TrackEntity> track = jpaTrackRepository.findById(id);
        return track.map(this::track);
    }

    @Override
    public void save(Track track) {
        track.events().forEach(trackPersistenceHandler::handle);
    }

    private Track track(TrackEntity trackEntity) {
        final TrackDataEntity trackDataEntity = trackEntity.getTrackDataEntity();
        final TrackDataPath trackDataPath = new TrackDataPath(trackDataEntity.getPath(), trackDataEntity.getName(), trackDataEntity.getExtension());
        final ListeningCounter listeningCounter = new ListeningCounter(trackEntity.getTrackCounterEntity().getCounter());
        return Track.restore(trackEntity.getId(), trackEntity.getName(), trackDataPath, listeningCounter);
    }
}
