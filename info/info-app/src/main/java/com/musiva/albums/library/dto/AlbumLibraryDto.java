package com.musiva.albums.library.dto;

import com.musiva.albums.library.vo.AlbumId;
import com.musiva.albums.library.vo.UserId;

import java.util.Set;
import java.util.UUID;

public record AlbumLibraryDto(UUID id, UserId userId, Set<AlbumId> albums) { }
