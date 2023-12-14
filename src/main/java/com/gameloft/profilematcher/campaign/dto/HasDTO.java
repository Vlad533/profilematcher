package com.gameloft.profilematcher.campaign.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class HasDTO {
    @SerializedName("country")
    private List<String> countries;
    private List<String> items;
}
