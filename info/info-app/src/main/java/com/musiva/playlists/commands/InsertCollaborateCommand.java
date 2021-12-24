package com.musiva.playlists.commands;

import com.musiva.playlists.vo.UserId;

import java.util.UUID;

public record InsertCollaborateCommand(UUID id, UserId collaborate) { }
