package com.papack.test;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.Villager;

public class PlayerDataManager {

    public static void dataManager(LivingEntity entity, ServerPlayer sourcePlayer) {

        sourcePlayer.sendSystemMessage(Component.literal("killed: " + entity.getName().getString()), false);

        int currentValue = ((IModPropertiesServerPlayer) sourcePlayer).test$getCustomIntData();

        int rewardPoint = 0;

        if (entity instanceof Zombie) {
            rewardPoint = 1;
        }
        if (entity instanceof Skeleton) {
            rewardPoint = 5;
        }
        if (entity instanceof Villager) {
            rewardPoint = -currentValue;
        }
        ((IModPropertiesServerPlayer) sourcePlayer).test$setCustomIntData(currentValue + rewardPoint);
    }
}