package com.papack.survivalstrategy;

import com.papack.survivalstrategy.fields.DataPool;
import com.papack.survivalstrategy.fields.Fields;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static com.papack.survivalstrategy.fields.Fields.registeredPlayer;
import static com.papack.survivalstrategy.fields.Fields.remainingTime;

public class Utils {

    public static boolean isRegisteredPlayer(IModPropertiesServerPlayer iPlayer) {
        DataPool dataPool = iPlayer.$_getDataPool();
        return (Boolean) dataPool.getValue(Fields.registeredPlayer);
    }

    public static void initializeThePlayer(IModPropertiesServerPlayer iPlayer) {
        DataPool dataPool = iPlayer.$_getDataPool();

        // Assign default settings to players participating for the first time.
        dataPool.setValue(Fields.survivalTime, 0);
        dataPool.setValue(remainingTime, RewardConfig.getGameTime(2, 0, 0));    // initial spawn : 2 days

        // Change to "Registered"
        dataPool.setValue(registeredPlayer, true);
    }

    public static void ban(ServerPlayer serverPlayer) {
        serverPlayer.sendSystemMessage(Component.literal("GAME OVER"), true);
    }
}
