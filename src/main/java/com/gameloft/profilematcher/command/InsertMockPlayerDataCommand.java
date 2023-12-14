package com.gameloft.profilematcher.command;

import com.gameloft.profilematcher.player.model.ClanModel;
import com.gameloft.profilematcher.player.model.DeviceModel;
import com.gameloft.profilematcher.player.model.PlayerModel;
import com.gameloft.profilematcher.player.service.PlayerService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.gameloft.profilematcher.util.DateTimeUtils.convertDateStringToDate;

@Component
public class InsertMockPlayerDataCommand implements CommandLineRunner {
    private final PlayerService playerService;

    public InsertMockPlayerDataCommand(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void run(String... args) {
        saveMockPlayerToDatabase();
    }

    public void saveMockPlayerToDatabase() {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setPlayerId("97983be2-98b7-11e7-90cf-082e5f28d836");
        playerModel.setCredential("apple_credential");
        playerModel.setCreated(convertDateStringToDate("2021-01-10 13:37:17Z"));
        playerModel.setModified(convertDateStringToDate("2021-01-23 13:37:17Z"));
        playerModel.setLastSession(convertDateStringToDate("2021-01-23 13:37:17Z"));
        playerModel.setTotalSpent(400);
        playerModel.setTotalRefund(0);
        playerModel.setTotalTransactions(5);
        playerModel.setLastPurchase(convertDateStringToDate("2021-01-23 13:37:17Z"));
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setModel("apple iphone 11");
        deviceModel.setCarrier("vodafone");
        deviceModel.setFirmware("123");
        playerModel.setDevices(List.of(deviceModel));
        playerModel.setLevel(3);
        playerModel.setXp(1000);
        playerModel.setTotalPlaytime(144);
        playerModel.setCountry("CA");
        playerModel.setLanguage("fr");
        playerModel.setBirthdate(convertDateStringToDate("2000-01-10 13:37:17Z"));
        playerModel.setGender("male");
        Gson jsonParser = new Gson();
        List<String> activeCampaignsJson = new ArrayList<>();
        playerModel.setActiveCampaignsJson(jsonParser.toJson(activeCampaignsJson));
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("cash", 123);
        inventory.put("coins", 1234);
        inventory.put("item_1", 1);
        inventory.put("item_34", 3);
        inventory.put("item_55", 2);
        playerModel.setInventoryJson(jsonParser.toJson(inventory));
        ClanModel clanModel = new ClanModel();
        clanModel.setName("Hello world clan");
        playerModel.setClan(clanModel);
        Map<String, String> customFields = new HashMap<>();
        customFields.put("_customfield", "mycustom");
        playerModel.setCustomFieldsJson(jsonParser.toJson(customFields));
        playerService.savePlayer(playerModel);
    }
}
