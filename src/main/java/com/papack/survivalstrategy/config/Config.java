package com.papack.survivalstrategy.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.papack.survivalstrategy.SurvivalStrategy.LOGGER;


public class Config {

    // Config File Location     ---------------------------------------------------------------------------------------
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("SurvivalStrategy");
    private static final File FILE = CONFIG_DIR.resolve("config.json").toFile();

    // record - for Equipments  ---------------------------------------------------------------------------------------
    public record InitialEquipments(String itemId, int slot, int lot) {
    }

    // record - for Initial Remaining Time (gameTime)
    public record InitialRemainingTime(int day, int hour, int minute) {
    }

    // record - for Reward , Penalty  (gameTime)
    public record RewardTime(int day, int hour, int minute) {
    }

    // Mod Settings             ---------------------------------------------------------------------------------------
    public List<InitialEquipments> playerInitialEquipmentsList = new ArrayList<>(Arrays.asList(
            new InitialEquipments("minecraft:wooden_axe", 0, 1),
            new InitialEquipments("minecraft:wooden_pickaxe", 1, 1),
            new InitialEquipments("minecraft:leather_boots", 36, 1),
            new InitialEquipments("minecraft:bread", 8, 5)
    ));

    public boolean useInitialEquipments = true;

    public InitialRemainingTime initialRemainingTime = new InitialRemainingTime(2, 0, 0);

    // Rewards
    public RewardTime spider = new RewardTime(0, 0, 5);
    public RewardTime skeleton = new RewardTime(0, 0, 20);
    public RewardTime creeper = new RewardTime(0, 0, 20);
    public RewardTime zombie = new RewardTime(0, 0, 30);
    public RewardTime player = new RewardTime(0, 0, 40);
    public RewardTime enderMan = new RewardTime(0, 0, 50);
    public RewardTime witherBoss = new RewardTime(0, 4, 0);
    public RewardTime enderDragon = new RewardTime(1, 0, 30);
    public RewardTime warden = new RewardTime(2, 0, 0);
    public RewardTime monster = new RewardTime(0, 0, 3);

    // Penalty
    public RewardTime animal = new RewardTime(-1, 0, 0);


    // Config File Operation    ---------------------------------------------------------------------------------------
    public static Config load() {
        if (FILE.exists()) {
            try (FileReader reader = new FileReader(FILE)) {
                Config config = GSON.fromJson(reader, Config.class);
                if (config != null) return config;
            } catch (Exception e) {
                LOGGER.error("Failed to load config", e);
            }
        }
        Config defaultConfig = new Config();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        try {
            if (Files.notExists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR);
            }
            try (FileWriter writer = new FileWriter(FILE)) {
                GSON.toJson(this, writer);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to save config", e);
        }
    }
}