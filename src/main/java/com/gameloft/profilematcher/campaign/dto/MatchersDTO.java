package com.gameloft.profilematcher.campaign.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MatchersDTO {
    private LevelDTO level;
    private HasDTO has;
    @SerializedName("does_not_have")
    private DoesNotHaveDTO doesNotHave;
}
