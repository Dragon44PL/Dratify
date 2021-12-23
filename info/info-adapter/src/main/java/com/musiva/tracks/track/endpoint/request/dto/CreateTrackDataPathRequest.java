package com.musiva.tracks.track.endpoint.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrackDataPathRequest {
    private String path;
    private String filename;
    private String extension;
}