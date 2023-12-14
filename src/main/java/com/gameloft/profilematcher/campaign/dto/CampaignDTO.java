package com.gameloft.profilematcher.campaign.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CampaignDTO {
    private String game;
    private String name;
    private double priority;
    private MatchersDTO matchers;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    private boolean enabled;
    @SerializedName("last_updated")
    private String lastUpdated;
}
