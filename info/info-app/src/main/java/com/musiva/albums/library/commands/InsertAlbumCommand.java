package com.musiva.albums.library.commands;

import com.musiva.albums.library.vo.AlbumId;

import java.util.UUID;

public record InsertAlbumCommand(UUID id, AlbumId album) { }
