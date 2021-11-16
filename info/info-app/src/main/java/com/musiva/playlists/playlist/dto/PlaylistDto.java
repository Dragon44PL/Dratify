package com.musiva.playlists.playlist.dto;

import com.musiva.playlists.playlist.vo.TrackId;
import com.musiva.playlists.playlist.vo.UserId;

import java.util.Set;
import java.util.UUID;

public record PlaylistDto(UUID id, String name, UserId author, Set<UserId>collaborators, Set<TrackId> tracks) { }
