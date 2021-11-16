package com.musiva.playlists.playlist.commands;

import com.musiva.playlists.playlist.vo.TrackId;

import java.util.UUID;

public record InsertTrackCommand(UUID id, TrackId track) { }
