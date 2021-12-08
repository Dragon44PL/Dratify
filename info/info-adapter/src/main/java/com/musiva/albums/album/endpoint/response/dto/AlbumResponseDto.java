package com.musiva.albums.album.endpoint.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponseDto {
    private UUID id;
    private String name;
    private UUID artistId;
    private Set<UUID> tracks;
}
