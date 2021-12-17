package com.musiva.playlists.playlist.persistence;

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
public class PlaylistEntity {

    @Id
    private UUID id;

    private String name;

    private UUID authorId;

    @ElementCollection
    @CollectionTable(name = "playlist_collaborator", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "collaborator")
    private Set<UUID> collaborators;

    @ElementCollection
    @CollectionTable(name = "playlist_track", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "track")
    private Set<UUID> tracks;
}
