package com.papack.survivalstrategy;

import com.papack.survivalstrategy.fields.DataPool;
import com.papack.survivalstrategy.fields.Fields;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;

import static com.papack.survivalstrategy.fields.Fields.registeredPlayer;
import static com.papack.survivalstrategy.fields.Fields.remainingTime;

public class Utils {

    public static boolean isRegisteredPlayer(IModPropertiesServerPlayer iPlayer) {
        DataPool dataPool = iPlayer.$_getDataPool();
        return (Boolean) dataPool.getValue(Fields.registeredPlayer);
    }

    public static boolean isBannedPlayer(IModPropertiesServerPlayer iPlayer) {
        DataPool dataPool = iPlayer.$_getDataPool();
        return (Boolean) dataPool.getValue(Fields.flagBan);
    }

    public static void initializeThePlayer(IModPropertiesServerPlayer iPlayer, boolean banned) {
        DataPool dataPool = iPlayer.$_getDataPool();

        // Assign default settings to players participating for the first time.
        dataPool.setValue(Fields.flagBan, false);
        dataPool.setValue(Fields.survivalTime, 0);
        dataPool.setValue(remainingTime, SurvivalStrategy.PLAYER_INIT_REMAINING_TIME);

        // Change to "Registered"
        dataPool.setValue(registeredPlayer, true);

        // Forced Survival Mode
        if (iPlayer instanceof ServerPlayer serverPlayer) {
            serverPlayer.setGameMode(GameType.SURVIVAL);

            ServerPlayer.RespawnConfig respawnConfig = serverPlayer.getRespawnConfig();

            if (respawnConfig != null) {
                Vec3 pos = (banned) ? respawnConfig.respawnData().globalPos().pos().getCenter(): respawnConfig.respawnData().pos().getCenter();
                serverPlayer.setPos(pos);
            }
        }
    }

    public static void onPlayerDied(IModPropertiesServerPlayer iPlayer) {
        DataPool dataPool = iPlayer.$_getDataPool();

        int currentRemainingTime = (int) dataPool.getValue(remainingTime);
        int penalty = RewardConfig.getGameTime(2, 0, 0);
        dataPool.setValue(remainingTime, currentRemainingTime - penalty);
    }

    // Calculate game ticks that match the duration of real time.
    public static int convertRealTimeToGameTicks(int realDay, int realHour, int realMinute, int realSecond) {
        return (realDay * 1728000) + (realHour * 72000) + (realMinute * 1200) + (realSecond * 20);
    }
}
