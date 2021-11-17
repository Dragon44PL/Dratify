package com.musiva.tracks.track.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackEntity {

    @Id
    private UUID id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "track_data_id", referencedColumnName = "id")
    private TrackDataEntity trackDataEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "counter_id", referencedColumnName = "id")
    private TrackCounterEntity trackCounterEntity;
}
