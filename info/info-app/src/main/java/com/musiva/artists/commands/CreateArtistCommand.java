package com.musiva.artists.commands;

import com.musiva.artists.vo.ArtistType;

public record CreateArtistCommand(String name, ArtistType artistType) { }
