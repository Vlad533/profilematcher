package com.gameloft.profilematcher.campaign.service;

import com.gameloft.profilematcher.campaign.dto.CampaignDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.Collections;
import java.util.List;

@Service
public class CampaignService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignService.class);

    public List<CampaignDTO> getRunningCampaigns() {
        try {
            List<CampaignDTO> runningCampaigns;
            JsonReader reader = new JsonReader(new FileReader("mock-campaign-data.json"));
            runningCampaigns = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss'Z'")
                    .create()
                    .fromJson(reader, new TypeToken<List<CampaignDTO>>(){}.getType());
            return runningCampaigns;
        } catch (Exception e) {
            LOGGER.error("Could not get the running campaigns", e);
            return Collections.emptyList();
        }
    }
}
