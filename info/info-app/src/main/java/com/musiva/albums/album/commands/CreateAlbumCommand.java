package com.musiva.albums.album.commands;

import com.musiva.albums.album.vo.ArtistId;

public record CreateAlbumCommand(String name, ArtistId artistId) { }
