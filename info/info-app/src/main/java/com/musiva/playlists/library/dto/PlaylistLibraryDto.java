package com.musiva.playlists.library.dto;

import com.musiva.playlists.library.vo.PlaylistId;
import com.musiva.playlists.library.vo.UserId;

import java.util.Set;
import java.util.UUID;

public record PlaylistLibraryDto(UUID id, UserId userId, Set<PlaylistId> playlists) { }
