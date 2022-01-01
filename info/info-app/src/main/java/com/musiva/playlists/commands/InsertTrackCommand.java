package com.musiva.playlists.commands;

import com.musiva.playlists.vo.TrackId;

import java.util.UUID;

public record InsertTrackCommand(UUID id, TrackId track) { }
