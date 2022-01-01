package com.musiva.playlists.commands;

import com.musiva.playlists.vo.UserId;

public record CreatePlaylistCommand(String name, UserId author) { }