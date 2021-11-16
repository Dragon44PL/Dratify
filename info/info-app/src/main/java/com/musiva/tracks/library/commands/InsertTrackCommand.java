package com.musiva.tracks.library.commands;

import com.musiva.tracks.library.vo.TrackId;

import java.util.UUID;

public record InsertTrackCommand(UUID id, TrackId trackId) {}
