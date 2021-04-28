package com.dratify.domain.songs.library;

import com.dratify.domain.albums.library.vo.UserId;
import com.dratify.domain.songs.library.vo.SongId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class SongLibrary {

    private final UUID id;
    private final UserId user;
    private final Set<SongId> songs;

    SongLibrary(UUID id, UserId user, Set<SongId> libraries) {
        this.id = id;
        this.user = user;
        this.songs = new HashSet<>(libraries);
    }

    SongLibrary(UUID id, UserId user) {
        this.id = id;
        this.user = user;
        this.songs = new HashSet<>();
    }
}
