package com.musiva.tracks;

import com.musiva.tracks.commands.IncrementCounterCommand;
import com.musiva.tracks.event.TrackEvent;
import com.musiva.tracks.commands.CreateTrackCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrackFacadeProxy extends TrackFacade {

    public TrackFacadeProxy(TrackRepository trackRepository) {
        super(trackRepository);
    }

    @Override
    @Transactional
    public List<TrackEvent> createTrack(CreateTrackCommand createTrackCommand) {
        return super.createTrack(createTrackCommand);
    }

    @Override
    @Transactional
    public List<TrackEvent> incrementCounter(IncrementCounterCommand incrementCounterCommand) {
        return super.incrementCounter(incrementCounterCommand);
    }
}
