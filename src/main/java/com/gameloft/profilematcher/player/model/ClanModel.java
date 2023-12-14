package com.gameloft.profilematcher.player.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "clans")
public class ClanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clanSequenceGenerator")
    @SequenceGenerator(
            name = "clanSequenceGenerator",
            sequenceName = "clan_sequence_generator",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
}
