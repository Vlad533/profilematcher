package com.gameloft.profilematcher.player.dto;

import lombok.Data;

import java.util.List;

@Data
public class ActiveCampaignResponseDTO {
    private String playerId;
    private List<String> activeCampaigns;
}
