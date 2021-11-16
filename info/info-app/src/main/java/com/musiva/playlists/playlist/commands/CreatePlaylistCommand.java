package com.musiva.playlists.playlist.commands;

import com.musiva.playlists.playlist.vo.UserId;

public record CreatePlaylistCommand(String name, UserId author) { }