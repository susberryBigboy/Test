package com.papack.survivalstrategy;

import com.papack.survivalstrategy.config.Config;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;

import static com.papack.survivalstrategy.SurvivalStrategy.config;

public class RewardManager {
/*
    public static final int DAY_4 = 96000;
    public static final int DAY_2 = 48000;
    public static final int DAY_1 = 24000;

    public static final int HOUR_4 = 4000;
    public static final int HOUR_1 = 1000;

    public static final int MINUTES_50 = 834;
    public static final int MINUTES_40 = 667;
    public static final int MINUTES_30 = 500;
    public static final int MINUTES_20 = 334;
    public static final int MINUTES_10 = 167;
    public static final int MINUTES_5 = 84;
*/


    //  Calculating game time
    //  1 day = 24,000 ticks
    //  1 hour = 1,000 ticks
    //  30 minutes = 500 ticks
    public static int getGameTime(int gameDay, int gameHour, int gameMinute) {
        return (gameDay * 24000) + (gameHour * 1000) + (1000 / 60 * gameMinute);
    }


    public static int getReward(LivingEntity entity) {

        // Reward
        /*if (entity instanceof Spider) return rewardTime(config.spider);
        if (entity instanceof AbstractSkeleton) return rewardTime(config.skeleton);
        if (entity instanceof Creeper) return rewardTime(config.creeper);
        if (entity instanceof Zombie) return rewardTime(config.zombie);*/

        if (entity instanceof Player) return rewardTime(config.player);
        if (entity instanceof EnderMan) return rewardTime(config.enderMan);
        if (entity instanceof WitherBoss) return rewardTime(config.witherBoss);
        if (entity instanceof EnderDragon) return rewardTime(config.enderDragon);
        if (entity instanceof Warden) return rewardTime(config.warden);

        if (entity instanceof Monster)
            return (int) (rewardTime(config.zombie) * RewardCalculator.calculateRewardPoints(entity));    // (Mobs other than the above)


        // Penalty
        if (entity instanceof AgeableMob
                || entity instanceof Animal
                || entity instanceof AmbientCreature
                || entity instanceof WaterAnimal) return rewardTime(config.animal);


        // None applicable
        return 0;
    }

    private static int rewardTime(Config.RewardTime rewardTime) {
        return getGameTime(rewardTime.day(), rewardTime.hour(), rewardTime.minute());
    }


}