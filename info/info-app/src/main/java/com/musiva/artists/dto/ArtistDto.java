package com.musiva.artists.dto;

import com.musiva.artists.vo.ArtistType;

import java.util.UUID;

public record ArtistDto(UUID id, String name, ArtistType artistType) { }
