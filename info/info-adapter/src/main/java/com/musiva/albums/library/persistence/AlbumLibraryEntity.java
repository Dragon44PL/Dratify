package com.musiva.albums.library.persistence;

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
public class AlbumLibraryEntity {

    @Id
    private UUID id;

    private UUID userId;

    @ElementCollection
    @CollectionTable(name = "album_user", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "album")
    private Set<UUID> albums;
}
