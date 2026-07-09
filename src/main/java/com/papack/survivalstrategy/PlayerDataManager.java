package com.papack.survivalstrategy;

import com.papack.survivalstrategy.fields.DataPool;
import com.papack.survivalstrategy.fields.Fields;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import static com.papack.survivalstrategy.RewardConfig.*;

public class PlayerDataManager {

    public static void onEntityKill(LivingEntity entity, ServerPlayer sourcePlayer) {

        if (sourcePlayer instanceof IModPropertiesServerPlayer iPlayer) {

            // Retrieving custom data.
            int currentRemainingTime = (int) iPlayer.$_getPoolData(Fields.remainingTime);
            int rewardPoint = getReward(entity);

            // Updating player custom data.
            // Since NBT data updates rely on vanilla mechanics, only player data is updated.

            DataPool dataPool = iPlayer.$_getDataPool();

            int updatedRemainingTime = Math.max(0, currentRemainingTime + rewardPoint);
            dataPool.setValue(Fields.remainingTime, updatedRemainingTime);

            // Send message - (Chat) - reward points
            if (updatedRemainingTime > 0) {
                SendMessage.sendChatMsgRewardPoint(sourcePlayer, entity, rewardPoint);
            }
        }
    }

}