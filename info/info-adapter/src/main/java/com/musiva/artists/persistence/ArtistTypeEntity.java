package com.musiva.artists.persistence;

import com.musiva.artists.vo.ArtistType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ArtistTypeEntity {
    private ArtistType type;
}
