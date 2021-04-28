package com.dratify.domain.albums.library;

import com.dratify.domain.albums.library.vo.LibraryId;
import com.dratify.domain.albums.library.vo.UserId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class AlbumLibrary {

    private final UUID id;
    private final UserId user;
    private final Set<LibraryId> libraries;

    AlbumLibrary(UUID id, UserId user, Set<LibraryId> libraries) {
        this.id = id;
        this.user = user;
        this.libraries = new HashSet<>(libraries);
    }

    AlbumLibrary(UUID id, UserId user) {
        this.id = id;
        this.user = user;
        this.libraries = new HashSet<>();
    }

}

