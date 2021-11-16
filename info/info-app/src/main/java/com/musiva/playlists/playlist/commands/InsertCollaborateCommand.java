package com.musiva.playlists.playlist.commands;

import com.musiva.playlists.playlist.vo.UserId;

import java.util.UUID;

public record InsertCollaborateCommand(UUID id, UserId collaborate) { }
