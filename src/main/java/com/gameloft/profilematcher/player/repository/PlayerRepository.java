package com.gameloft.profilematcher.player.repository;

import com.gameloft.profilematcher.player.dto.PlayerFieldsUsedForCampaignMatchingDTO;
import com.gameloft.profilematcher.player.model.PlayerModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerModel, Long> {
    @Query("select new com.gameloft.profilematcher.player.dto.PlayerFieldsUsedForCampaignMatchingDTO(player.id, player.playerId, player.level, player.activeCampaignsJson, player.inventoryJson, player.country) from PlayerModel player where player.playerId = :playerId")
    PlayerFieldsUsedForCampaignMatchingDTO getPlayerDataForCampaignProfileMatching(@Param("playerId") String playerId);

    @Transactional
    @Query("update PlayerModel player set player.activeCampaignsJson = :activeCampaigns where player.playerId = :playerId")
    @Modifying
    void updateActiveCampaignsOnPlayerProfile(
            @Param("activeCampaigns") String activeCampaigns,
            @Param("playerId") String playerId
    );
}
