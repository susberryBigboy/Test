package com.papack.survivalstrategy;

import com.papack.survivalstrategy.fields.DataPool;
import com.papack.survivalstrategy.fields.Fields;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class ServerTick {

    public static void onServerTick(MinecraftServer server) {

        List<ServerPlayer> playerList = server.getPlayerList().getPlayers();
        if (!playerList.isEmpty()) {
            for (ServerPlayer serverPlayer : playerList) {
                if (serverPlayer instanceof IModPropertiesServerPlayer iPlayer) {

                    if (Utils.isRegisteredPlayer(iPlayer)) {

                        DataPool dataPool = iPlayer.$_getDataPool();
                        boolean flagBan = (boolean) dataPool.getValue(Fields.flagBan);


                        // <<< GAME OVER >>> \\
                        if (!flagBan && (int) dataPool.getValue(Fields.remainingTime) <= 0) {

                            dataPool.setValue(Fields.flagBan, true);
                            GameOverHandler.handleGameOver(serverPlayer, (int) dataPool.getValue(Fields.survivalTime));
                        }
                    }
                }
            }
        }
    }
}
