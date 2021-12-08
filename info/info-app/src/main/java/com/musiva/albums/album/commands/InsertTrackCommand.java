package com.musiva.albums.album.commands;

import com.musiva.albums.album.vo.TrackId;

import java.util.UUID;

public record InsertTrackCommand(UUID id, TrackId track) { }
