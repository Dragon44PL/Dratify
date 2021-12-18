package com.musiva.playlists.endpoint.response.dto;

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
public class PlaylistResponse {
    private UUID id;
    private String name;
    private UUID authorId;
    private Set<UUID> collaborators;
    private Set<UUID> tracks;
}