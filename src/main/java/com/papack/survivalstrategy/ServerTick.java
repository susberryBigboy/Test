package com.papack.survivalstrategy;

import com.papack.survivalstrategy.fields.Fields;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class ServerTick {

    public static void onServerTick(MinecraftServer server) {

        List<ServerPlayer> playerList = server.getPlayerList().getPlayers();
        if (!playerList.isEmpty()) {
            for (ServerPlayer player : playerList) {
                if (player instanceof IModPropertiesServerPlayer iPlayer) {

                    if (Utils.isRegisteredPlayer(iPlayer)) {

                        if ((int) iPlayer.$_getDataPool().getValue(Fields.remainingTime) <= 0) {
                            Utils.ban(player);
                        }
                    }
                }
            }
        }
    }
}
