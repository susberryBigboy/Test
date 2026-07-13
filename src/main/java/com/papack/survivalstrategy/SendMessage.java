package com.papack.survivalstrategy;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class SendMessage {

    public static void sendChatMsgRewardPoint(ServerPlayer serverPlayer, LivingEntity mob, int rewardPoint) {
        MutableComponent msg = Component.literal("killed: " + mob.getName().getString())
                .append("  rewardPoint: " + rewardPoint);
        serverPlayer.sendSystemMessage(msg, false);
    }

    public static void sendSystemMsgCurrentValues(ServerPlayer serverPlayer, int remainingTime , int survivalTime) {
        if (remainingTime > 0){
        MutableComponent msg =
                Component.literal("Remaining: " + remainingTime)
                        .append("  Score: " + survivalTime)
                        .withStyle(ChatFormatting.YELLOW)
                        .withStyle(ChatFormatting.BOLD);

            serverPlayer.sendSystemMessage(msg, true);
        }
    }
}
