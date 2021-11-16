package com.musiva.albums.album.dto;

import com.musiva.albums.album.vo.ArtistId;
import com.musiva.albums.album.vo.TrackId;

import java.util.Set;
import java.util.UUID;

public record AlbumDto(UUID id, String name, Set<TrackId> tracks, ArtistId artist) { }
