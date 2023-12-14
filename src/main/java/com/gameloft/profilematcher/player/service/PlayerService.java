package com.gameloft.profilematcher.player.service;

import com.gameloft.profilematcher.campaign.dto.*;
import com.gameloft.profilematcher.campaign.service.CampaignService;
import com.gameloft.profilematcher.player.dto.PlayerFieldsUsedForCampaignMatchingDTO;
import com.gameloft.profilematcher.player.model.PlayerModel;
import com.gameloft.profilematcher.player.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class PlayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    private final CampaignService campaignService;
    private final PlayerRepository playerRepository;

    public PlayerService(CampaignService campaignService, PlayerRepository playerRepository) {
        this.campaignService = campaignService;
        this.playerRepository = playerRepository;
    }

    public List<String> getMatchingCampaignsForPlayer(PlayerFieldsUsedForCampaignMatchingDTO player) {
        try {
            if (player == null || StringUtils.isEmpty(player.getPlayerId())) {
                return Collections.emptyList();
            }
            List<CampaignDTO> allRunningCampaigns = campaignService.getRunningCampaigns();
            if (CollectionUtils.isEmpty(allRunningCampaigns)) {
                return Collections.emptyList();
            }
            LOGGER.info("All running campaigns {}", allRunningCampaigns);
            List<String> matchingCampaignsOnPlayerProfile = allRunningCampaigns.stream()
                    .filter(Objects::nonNull)
                    .filter(campaignDTO -> playerProfileMatchesAnyOfTheCampaignRequirements(player, campaignDTO.getMatchers()))
                    .map(CampaignDTO::getName)
                    .toList();
            if(!CollectionUtils.isEmpty(matchingCampaignsOnPlayerProfile)) {
                updatePlayerActiveCampaigns(player.getPlayerId(), matchingCampaignsOnPlayerProfile);
            }
            return matchingCampaignsOnPlayerProfile;
        } catch (Exception e) {
            LOGGER.error("Could not get the matching campaigns for player {}", player, e);
            return Collections.emptyList();
        }
    }

    private boolean playerProfileMatchesAnyOfTheCampaignRequirements(PlayerFieldsUsedForCampaignMatchingDTO player, MatchersDTO campaignMatchers) {
        if (player != null && StringUtils.isNotEmpty(player.getPlayerId()) && campaignMatchers != null) {
            LevelDTO campaignLevel = campaignMatchers.getLevel();
            if (campaignLevel != null) {
                if (playerLevelMatchesCampaignLevelRange(player.getLevel(), campaignLevel)) {
                    return true;
                }
            }
            HasDTO campaignCountriesAndIncludedInventoryItems = campaignMatchers.getHas();
            Map<String, Integer> playerInventory = convertJsonStringToObject(player.getInventoryJson(), new TypeToken<>() {});
            if (playerInventory != null) {
                DoesNotHaveDTO campaignExcludedInventoryItems = campaignMatchers.getDoesNotHave();
                if (campaignExcludedInventoryItems != null) {
                    List<String> excludedItems = campaignExcludedInventoryItems.getItems();
                    if (!CollectionUtils.isEmpty(excludedItems)) {
                        if (playerInventoryDoesNotContainTheCampaignExcludedItems(playerInventory, excludedItems)) {
                            return true;
                        }
                    }
                }
                if (campaignCountriesAndIncludedInventoryItems != null) {
                    if (playerCountryMatchesCampaignCountries(player.getCountry(), campaignCountriesAndIncludedInventoryItems.getCountries())) {
                        return true;
                    }
                    List<String> includedItems = campaignCountriesAndIncludedInventoryItems.getItems();
                    if (!CollectionUtils.isEmpty(includedItems)) {
                        return playerInventoryContainsTheCampaignIncludedInventoryItems(playerInventory, includedItems);
                    }
                }
            }
        }
        return false;
    }

    private boolean playerInventoryContainsTheCampaignIncludedInventoryItems(Map<String, Integer> playerInventory,
                                                                             List<String> campaignIncludedItems) {
        if (playerInventory.isEmpty()) {
            return false;
        }
        for (String item : campaignIncludedItems) {
            if (!playerInventory.containsKey(item)) {
                return false;
            }
        }
        return true;
    }

    private boolean playerInventoryDoesNotContainTheCampaignExcludedItems(Map<String, Integer> playerInventory,
                                                                          List<String> campaignExcludedItems) {
        if (playerInventory.isEmpty()) {
            return true;
        }
        for (String item : campaignExcludedItems) {
            if (playerInventory.containsKey(item)) {
                return false;
            }
        }
        return true;
    }

    private boolean playerLevelMatchesCampaignLevelRange(int playerLevel, LevelDTO campaignLevelRange) {
        int minimumLevelRequired = campaignLevelRange.getMin();
        int maximumLevelRequired = campaignLevelRange.getMax();
        return playerLevel >= minimumLevelRequired &&
                playerLevel <= maximumLevelRequired;
    }

    private boolean playerCountryMatchesCampaignCountries(String playerCountry, List<String> campaignCountries) {
        return campaignCountries.contains(playerCountry);
    }

    private <T> T convertJsonStringToObject(String jsonString, TypeToken<T> typeToken) {
        return new Gson().fromJson(jsonString, typeToken.getType());
    }

    @Transactional
    public void savePlayer(PlayerModel playerModel) {
        if(playerModel != null) {
            playerRepository.save(playerModel);
        }
    }

    @Transactional
    public void updatePlayerActiveCampaigns(String playerId, List<String> activeCampaignNames) {
        playerRepository.updateActiveCampaignsOnPlayerProfile(new Gson().toJson(activeCampaignNames), playerId);
    }

    public Optional<PlayerFieldsUsedForCampaignMatchingDTO> getPlayerFromDatabaseForCampaignMatching(String playerId) {
        return Optional.ofNullable(playerRepository.getPlayerDataForCampaignProfileMatching(playerId));
    }
}
