package com.musiva.tracks.library.commands;

import com.musiva.tracks.library.vo.TrackId;
import com.musiva.tracks.library.vo.UserId;

public record InsertTrackCommand(UserId userId, TrackId trackId) {}
