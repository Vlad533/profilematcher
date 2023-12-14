package com.gameloft.profilematcher.player.controller;

import com.gameloft.profilematcher.player.dto.ActiveCampaignResponseDTO;
import com.gameloft.profilematcher.player.dto.PlayerFieldsUsedForCampaignMatchingDTO;
import com.gameloft.profilematcher.util.dto.GenericControllerResponseDTO;
import com.gameloft.profilematcher.player.service.PlayerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.gameloft.profilematcher.util.ControllerUtils.constructGenericControllerResponse;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{player_id}/active-campaigns")
    public ResponseEntity<GenericControllerResponseDTO<ActiveCampaignResponseDTO>> getActiveCampaignsForPlayer(
            @PathVariable("player_id") String playerId
    ) {
        try {
            if (StringUtils.isEmpty(playerId)) {
                return new ResponseEntity<>(
                        constructGenericControllerResponse(null, "The \"player_id\" parameter was not provided"),
                        HttpStatus.BAD_REQUEST
                );
            }
            Optional<PlayerFieldsUsedForCampaignMatchingDTO> playerFromDatabaseOptional = playerService.getPlayerFromDatabaseForCampaignMatching(playerId);
            if (playerFromDatabaseOptional.isEmpty()) {
                return new ResponseEntity<>(
                        constructGenericControllerResponse(null, String.format("The player with id %s was not found", playerId)),
                        HttpStatus.BAD_REQUEST
                );
            }
            PlayerFieldsUsedForCampaignMatchingDTO playerFromDatabase = playerFromDatabaseOptional.get();
            LOGGER.info("Player from database {}", playerFromDatabase);
            List<String> matchingCampaignsForPlayer = playerService.getMatchingCampaignsForPlayer(playerFromDatabase);
            ActiveCampaignResponseDTO activeCampaignResponseDTO = new ActiveCampaignResponseDTO();
            activeCampaignResponseDTO.setPlayerId(playerId);
            activeCampaignResponseDTO.setActiveCampaigns(matchingCampaignsForPlayer);
            return new ResponseEntity<>(
                    constructGenericControllerResponse(activeCampaignResponseDTO, "Success"),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            LOGGER.error("Could not get the running campaigns for profile with id {}", playerId, e);
            return new ResponseEntity<>(
                    constructGenericControllerResponse(null, String.format("The player with id %s was not found", playerId)),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
