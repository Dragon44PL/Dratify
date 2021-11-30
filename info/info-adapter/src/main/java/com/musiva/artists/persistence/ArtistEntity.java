package com.musiva.artists.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistEntity {

    @Id
    private UUID id;

    private String name;

    @Embedded
    private ArtistTypeEntity artistTypeEntity;
}
