package com.papack.survivalstrategy;

import com.papack.survivalstrategy.fields.DataPool;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;

import static com.papack.survivalstrategy.RewardConfig.*;
import static com.papack.survivalstrategy.fields.Fields.*;

public class PlayerDataManager {

    public static void onEntityKill(LivingEntity entity, ServerPlayer sourcePlayer) {

        if (sourcePlayer instanceof IModPropertiesServerPlayer iPlayer) {

            // Retrieving custom data.
            int currentValue = (int) iPlayer.$_getPoolData(remainingTime);

            int rewardPoint = getReward(entity);

            // Updating player custom data.
            // Since NBT data updates rely on vanilla mechanics, only player data is updated.

            DataPool dataPool = iPlayer.$_getDataPool();

            int value = currentValue + rewardPoint;
            dataPool.setValue(remainingTime, value);
            GlobalScoreboardManager.updatePlayerData(sourcePlayer, value);

            sourcePlayer.sendSystemMessage(Component.literal("killed: " + entity.getName().getString()), false);
            sourcePlayer.sendSystemMessage(Component.literal("Score: " + value), false);

        }
    }

    private static int getReward(LivingEntity entity) {

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


        // Penalty
        if (entity instanceof AgeableMob
                || entity instanceof Animal
                || entity instanceof AmbientCreature
                || entity instanceof WaterAnimal) return -getGameTime(1, 0, 0);


        // None
        return 0;
    }
}