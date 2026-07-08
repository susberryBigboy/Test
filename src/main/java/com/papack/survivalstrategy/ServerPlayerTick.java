package com.papack.survivalstrategy;

import com.papack.survivalstrategy.fields.DataPool;
import com.papack.survivalstrategy.fields.Fields;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

public class ServerPlayerTick {

    public static void onServerPlayerTick(IModPropertiesServerPlayer iPlayer) {

        updateLifeTime(iPlayer);

    }


    private static void updateLifeTime(IModPropertiesServerPlayer iPlayer) {

        if (iPlayer instanceof ServerPlayer serverPlayer && serverPlayer.isAlive()) {

            DataPool dataPool = iPlayer.$_getDataPool();

            // Remaining Time - decrement
            int currentRemainingTime = (int) dataPool.getValue(Fields.remainingTime);
            int updatedRemainingTime = Math.max(-1, currentRemainingTime - 1);                  // Just to be safe, stop at -1 as a safeguard.

            dataPool.setValue(Fields.remainingTime, updatedRemainingTime);
            GlobalScoreboardManager.updatePlayerData(serverPlayer, updatedRemainingTime);


            // Survival Time - increment
            int currentSurvivalTime = (int) dataPool.getValue(Fields.survivalTime);
            int updatedSurvivalTime = currentSurvivalTime + 1;

            dataPool.setValue(Fields.survivalTime, updatedSurvivalTime);

            // send message - (Action Bar) - remaining time
            MutableComponent msg =
                    Component.literal("Remaining: " + updatedRemainingTime)
                            .append("  Survival: " + updatedSurvivalTime)
                            .withStyle(ChatFormatting.YELLOW)
                            .withStyle(ChatFormatting.BOLD);

            serverPlayer.sendSystemMessage(msg, true);
        }
    }
}