package com.musiva.albums.album.commands;

import com.musiva.albums.album.vo.TrackId;

import java.util.UUID;

public record InsertAlbumCommand(UUID id, TrackId track) { }
