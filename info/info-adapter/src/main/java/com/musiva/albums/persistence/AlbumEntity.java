package com.musiva.albums.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumEntity {

    @Id
    private UUID id;

    private String name;

    private UUID artistId;

    @ElementCollection
    @CollectionTable(name = "tracks", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "track")
    private Set<UUID> tracks;
}
