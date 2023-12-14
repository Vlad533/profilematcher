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
@Table(name = "players_devices")
public class DeviceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deviceSequenceGenerator")
    @SequenceGenerator(
            name = "deviceSequenceGenerator",
            sequenceName = "device_sequence_generator",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;
    private String model;
    private String carrier;
    private String firmware;
}
