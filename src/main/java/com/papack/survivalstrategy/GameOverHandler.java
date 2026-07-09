package com.papack.survivalstrategy;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class GameOverHandler {

    public static void handleGameOver(ServerPlayer serverPlayer, int value) {
        if (serverPlayer == null || serverPlayer.isRemoved()) {
            return;
        }

        // Force Kill
        forceKillThePlayer(serverPlayer);

        // 1. Change the player to spectator mode
        serverPlayer.setGameMode(GameType.SPECTATOR);

        // 2. Set title display duration (Fade-in: 10, Display: 80, Fade-out: 10 ticks = Total 100 ticks / 5 seconds)
        serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(10, 80, 10));

        // 3. Send the main title "GAME OVER"
        Component titleComponent = Component.literal("GAME OVER").withStyle(net.minecraft.ChatFormatting.RED, net.minecraft.ChatFormatting.BOLD);
        serverPlayer.connection.send(new ClientboundSetTitleTextPacket(titleComponent));

        // 4. Send subtitle "SCORE: [value]"
        Component subtitleComponent = Component.literal("SCORE: " + value).withStyle(net.minecraft.ChatFormatting.YELLOW);
        serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(subtitleComponent));

        // 5. Execute disconnection processing after 100 ticks.
        int delay = Utils.convertRealTimeToGameTicks(0, 0, 0, 5);

        TickScheduler.setDelayTask(delay, () -> {
            if (!serverPlayer.isRemoved() || serverPlayer.isDeadOrDying()) {
                // Message displayed on the screen upon disconnection
                Component kickMessage = Component.literal("GAME OVER \nscore: " + value);
                serverPlayer.connection.disconnect(kickMessage);
            }
        });
    }

    private static void forceKillThePlayer(ServerPlayer serverPlayer) {
        MinecraftServer server = serverPlayer.level().getServer();
        ResourceKey<Level> dimension = serverPlayer.level().dimension();
        ServerLevel serverLevel = server.getLevel(dimension);

        if (serverLevel != null) {
            serverPlayer.kill(serverLevel);
            serverPlayer.setRespawnPosition(null, false);
        }
    }
}