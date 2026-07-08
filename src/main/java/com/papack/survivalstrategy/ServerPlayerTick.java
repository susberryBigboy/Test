package com.papack.survivalstrategy;

import com.papack.survivalstrategy.fields.DataPool;
import com.papack.survivalstrategy.fields.Fields;
import net.minecraft.server.level.ServerPlayer;

public class ServerPlayerTick {

    public static void onServerPlayerTick(IModPropertiesServerPlayer iPlayer) {

        updateLifeTime(iPlayer);

    }


    private static void updateLifeTime(IModPropertiesServerPlayer iPlayer) {

        if (((ServerPlayer) iPlayer).isAlive()) {

            DataPool dataPool = iPlayer.$_getDataPool();

            // Remaining Time - decrement
            int currentRemainingTime = (int) dataPool.getValue(Fields.remainingTime);
            int updatedRemainingTime = Math.max(0, currentRemainingTime - 1);

            dataPool.setValue(Fields.remainingTime, updatedRemainingTime);


            // Survival Time - increment
            int currentSurvivalTime = (int) dataPool.getValue(Fields.survivalTime);
            int updatedSurvivalTime = Math.max(0, currentSurvivalTime + 1);

            dataPool.setValue(Fields.survivalTime, updatedSurvivalTime);
        }
    }
}