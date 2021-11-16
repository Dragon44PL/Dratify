package com.musiva.playlists.library.commands;

import com.musiva.playlists.library.vo.PlaylistId;

import java.util.UUID;

public record InsertPlaylistCommand(UUID id, PlaylistId playlistId) { }
