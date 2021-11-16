package com.musiva.tracks.track.commands;

import com.musiva.tracks.track.vo.TrackDataPath;

public record CreateTrackCommand(String name, TrackDataPath trackDataPath) { }
