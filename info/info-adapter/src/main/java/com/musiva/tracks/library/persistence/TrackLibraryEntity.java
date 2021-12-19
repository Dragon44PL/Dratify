package com.musiva.tracks.library.persistence;

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
public class TrackLibraryEntity {

    @Id
    private UUID id;

    private UUID userId;

    @ElementCollection
    @CollectionTable(name = "track_user", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "track")
    private Set<UUID> tracks;
}
