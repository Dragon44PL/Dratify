package com.musiva.tracks.endpoint.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackResponseDto {
    private String id;
    private String name;
    private long counter;
}
