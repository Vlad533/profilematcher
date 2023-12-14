package com.gameloft.profilematcher.player.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerFieldsUsedForCampaignMatchingDTO {
    private Long id;
    private String playerId;
    private int level;
    private String activeCampaignsJson;
    private String inventoryJson;
    private String country;
}
