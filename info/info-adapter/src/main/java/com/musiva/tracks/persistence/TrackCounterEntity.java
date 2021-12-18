package com.musiva.tracks.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackCounterEntity {

    @Id
    private UUID id;

    @OneToOne(mappedBy = "trackCounterEntity")
    private TrackEntity trackEntity;

    private long counter;
}
