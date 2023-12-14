package com.gameloft.profilematcher.player.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "players")
public class PlayerModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playerSequenceGenerator")
    @SequenceGenerator(
            name = "playerSequenceGenerator",
            sequenceName = "player_sequence_generator",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String playerId;
    private String credential;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSession;

    private int totalSpent;
    private int totalRefund;
    private int totalTransactions;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPurchase;

    @Column(columnDefinition = "TEXT")
    private String activeCampaignsJson;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<DeviceModel> devices;

    private int level;
    private int xp;
    private int totalPlaytime;
    private String country;
    private String language;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthdate;

    private String gender;

    @Column(columnDefinition = "TEXT")
    private String inventoryJson;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "clan_id", referencedColumnName = "id")
    private ClanModel clan;

    private String customFieldsJson;
}
