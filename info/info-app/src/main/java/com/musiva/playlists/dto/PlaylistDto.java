package com.musiva.playlists.dto;

import com.musiva.playlists.vo.TrackId;
import com.musiva.playlists.vo.UserId;

import java.util.Set;
import java.util.UUID;

public record PlaylistDto(UUID id, String name, UserId author, Set<UserId>collaborators, Set<TrackId> tracks) { }
