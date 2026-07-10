package com.papack.survivalstrategy;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;

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
        if (entity instanceof Spider) return getGameTime(0, 0, 5);
        if (entity instanceof Skeleton) return getGameTime(0, 0, 20);
        if (entity instanceof Creeper) return getGameTime(0, 0, 20);
        if (entity instanceof Zombie) return getGameTime(0, 0, 30);
        if (entity instanceof Player) return getGameTime(0, 0, 40);
        if (entity instanceof EnderMan) return getGameTime(0, 0, 50);
        if (entity instanceof WitherBoss) return getGameTime(0, 4, 0);
        if (entity instanceof EnderDragon) return getGameTime(1, 0, 30);
        if (entity instanceof Warden) return getGameTime(2, 0, 0);

        if (entity instanceof Monster) return getGameTime(0, 0, 3);     // (Mobs other than the above)


        // Penalty
        if (entity instanceof AgeableMob
                || entity instanceof Animal
                || entity instanceof AmbientCreature
                || entity instanceof WaterAnimal) return -getGameTime(1, 0, 0);


        // None applicable
        return 0;
    }
}