package com.papack.survivalstrategy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import java.util.HashMap;

import static com.papack.survivalstrategy.fields.Fields.remainingTime;

public class SurvivalStrategy implements ModInitializer {

    public static final String MOD_ID = "survivalstrategy";

    @Override
    public void onInitialize() {

        // Value modification upon player death
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {

            if (entity instanceof IModPropertiesServerPlayer iPlayer) {

                int value = (int) iPlayer.$_getPoolData(remainingTime);
                int modifiedValue = Math.max(0, value - 5);

                iPlayer.$_setPoolData(remainingTime, modifiedValue);

                // (The result is the same even if you write it this way.)
                // iPlayer.$_getDataPool().$_setValue(lifeCounter, modifiedValue);

            }

            return true;
        });

        // Carry over data upon respawning.
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {

            if (oldPlayer instanceof IModPropertiesServerPlayer iOldPlayer
                    && newPlayer instanceof IModPropertiesServerPlayer iNewPlayer) {

                // To prevent bugs caused by conflicts between vanilla and modded content arising
                // from shared data references, a copy is created using `new HashMap<>()` and set.
                iNewPlayer.$_getDataPool().setStringDataMap(new HashMap<>(iOldPlayer.$_getDataPool().getStringDataMap()));
                iNewPlayer.$_getDataPool().setIntDataMap(new HashMap<>(iOldPlayer.$_getDataPool().getIntDataMap()));
                iNewPlayer.$_getDataPool().setFloatDataMap(new HashMap<>(iOldPlayer.$_getDataPool().getFloatDataMap()));
                iNewPlayer.$_getDataPool().setDoubleDataMap(new HashMap<>(iOldPlayer.$_getDataPool().getDoubleDataMap()));
                iNewPlayer.$_getDataPool().setBooleanDataMap(new HashMap<>(iOldPlayer.$_getDataPool().getBooleanDataMap()));
            }
        });

        // Player Join Event
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {

            if (handler.player instanceof IModPropertiesServerPlayer iPlayer) {

                // Initialize if "registeredPlayer" is false.
                if (!Utils.isRegisteredPlayer(iPlayer)) {
                    Utils.initializeThePlayer(iPlayer);
                }
            }
        });

        // Server Tick Event
        ServerTickEvents.END_SERVER_TICK.register(ServerTick::onServerTick);

        // ScoreBoard Object
        ServerLifecycleEvents.SERVER_STARTED.register(GlobalScoreboardManager::initScoreboard);
    }
}