package com.dratify.domain.albums.library;

import com.dratify.domain.albums.library.event.AlbumAddedEvent;
import com.dratify.domain.albums.library.event.AlbumLibraryCreatedEvent;
import com.dratify.domain.albums.library.event.AlbumLibraryEvent;
import com.dratify.domain.albums.library.event.AlbumRemovedEvent;
import com.dratify.domain.albums.library.vo.AlbumId;
import com.dratify.domain.albums.library.vo.UserId;
import domain.AggregateRoot;
import java.util.*;

class AlbumLibrary extends AggregateRoot<UUID, AlbumLibraryEvent> {

    private final UUID id;
    private final UserId user;
    private final Set<AlbumId> albums;

    static AlbumLibrary create(UUID id, UserId user, Set<AlbumId> albums) {
        final AlbumLibrary albumLibrary = new AlbumLibrary(id, user, albums, new ArrayList<>());
        albumLibrary.registerEvent(new AlbumLibraryCreatedEvent(albumLibrary.id, albumLibrary.user, albumLibrary.albums));
        return albumLibrary;
    }

    static AlbumLibrary restore(UUID id, UserId user, Set<AlbumId> albums) {
        return new AlbumLibrary(id, user, albums, new ArrayList<>());
    }

    private AlbumLibrary(UUID id, UserId user, Set<AlbumId> albums, List<AlbumLibraryEvent> events) {
        super(events);
        this.id = id;
        this.user = user;
        this.albums = new HashSet<>(albums);
    }

    void addAlbum(AlbumId album) {
        if(!hasAlbum(album)) {
            processAddingAlbum(album);
        }
    }

    private void processAddingAlbum(AlbumId album) {
        albums.add(album);
        final AlbumAddedEvent albumAddedEvent = new AlbumAddedEvent(id, album);
        this.registerEvent(albumAddedEvent);
    }

    void removeAlbum(AlbumId album) {
        if(hasAlbum(album)) {
            processRemovingAlbum(album);
        }
    }

    private void processRemovingAlbum(AlbumId album) {
        albums.remove(album);
        final AlbumRemovedEvent albumRemovedEvent = new AlbumRemovedEvent(id, album);
        this.registerEvent(albumRemovedEvent);
    }

    boolean hasAlbum(AlbumId album) {
        return albums.contains(album);
    }

}

