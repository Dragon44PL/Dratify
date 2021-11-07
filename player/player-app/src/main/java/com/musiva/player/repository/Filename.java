package com.musiva.player.repository;

import java.util.UUID;

public record Filename(UUID name, String extension) {

    @Override
    public String toString() {
        return name.toString() + "." + extension;
    }
}