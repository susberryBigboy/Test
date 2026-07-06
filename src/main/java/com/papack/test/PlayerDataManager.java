package com.papack.test;

import com.papack.test.fields.DataPool;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.Villager;

import static com.papack.test.fields.Fields.*;

public class PlayerDataManager {

    public static void onEntityKill(LivingEntity entity, ServerPlayer sourcePlayer) {

        if (sourcePlayer instanceof IModPropertiesServerPlayer iPlayer) {

            // Retrieving custom data.
            int currentValue = (int) iPlayer.$_getPoolData(lifeCounter);

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

            // Updating player custom data.
            // Since NBT data updates rely on vanilla mechanics, only player data is updated.

            DataPool iDataPool = iPlayer.$_getDataPool();

            int value = currentValue + rewardPoint;
            iDataPool.setValue(lifeCounter, value);
            iDataPool.setValue(testIntField, value * 2);

            iDataPool.setValue(lastMob, entity.getName().getString());
            iDataPool.setValue(testStrField, entity.getName().getString() + "-test");

            iDataPool.setValue(lastReward, (float) rewardPoint);
            iDataPool.setValue(testFloatField, rewardPoint + 0.5f);


            sourcePlayer.sendSystemMessage(Component.literal("killed: " + entity.getName().getString()), false);
            sourcePlayer.sendSystemMessage(Component.literal("Score: " + value), false);

        }
    }
}