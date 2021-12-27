package com.musiva.albums.library.commands;

import com.musiva.albums.library.vo.AlbumId;
import com.musiva.albums.library.vo.UserId;

import java.util.UUID;

public record InsertAlbumCommand(UserId userId, AlbumId album) { }
