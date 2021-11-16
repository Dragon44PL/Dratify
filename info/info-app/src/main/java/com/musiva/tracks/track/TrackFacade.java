package com.musiva.tracks.track;

import com.musiva.tracks.track.commands.CreateTrackCommand;
import com.musiva.tracks.track.commands.IncrementCounterCommand;
import com.musiva.tracks.track.event.TrackEvent;
import com.musiva.tracks.track.exception.TrackAlreadyCreatedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TrackFacade {

    private final TrackRepository trackRepository;

    public TrackFacade(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<TrackEvent> createTrack(CreateTrackCommand createTrackCommand) {
        final Optional<Track> found = trackRepository.findByName(createTrackCommand.name());

        if (found.isPresent()) {
            throw new TrackAlreadyCreatedException("Track '" + createTrackCommand.name() + "' already created");
        }

        final UUID generatedId = UUID.randomUUID();
        final Track created = Track.create(generatedId, createTrackCommand.name(), createTrackCommand.trackDataPath());
        trackRepository.save(created);
        return created.events();
    }

    public List<TrackEvent> increamentCounter(IncrementCounterCommand incrementCounterCommand) {
        final Optional<Track> track = trackRepository.findById(incrementCounterCommand.id());
        return track.map(this::processIncrementingTrack).orElseGet(ArrayList::new);
    }

    private List<TrackEvent> processIncrementingTrack(Track track) {
        track.trackListened();
        trackRepository.save(track);
        return track.events();
    }

}
