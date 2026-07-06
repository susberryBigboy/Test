package com.papack.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

import java.util.HashMap;

import static com.papack.test.fields.Fields.lifeCounter;

public class Test implements ModInitializer {

    public static final String MOD_ID = "testmod";

    @Override
    public void onInitialize() {

        // Value modification upon player death
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {

            if (entity instanceof IModPropertiesServerPlayer iPlayer) {

                int value = (int) iPlayer.__getPoolData(lifeCounter);
                int modifiedValue = Math.max(0, value - 5);

                iPlayer.__setPoolData(lifeCounter, modifiedValue);

                // (The result is the same even if you write it this way.)
                // iPlayer.getDataPool().setValue(lifeCounter, modifiedValue);

            }

            return true;
        });

        // Carry over data upon respawning.
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {

            if (oldPlayer instanceof IModPropertiesServerPlayer iOldPlayer
                    && newPlayer instanceof IModPropertiesServerPlayer iNewPlayer) {

                // To prevent bugs caused by conflicts between vanilla and modded content arising
                // from shared data references, a copy is created using `new HashMap<>()` and set.
                iNewPlayer.__getDataPool().setStringDataMap(new HashMap<>(iOldPlayer.__getDataPool().getStringDataMap()));
                iNewPlayer.__getDataPool().setIntDataMap(new HashMap<>(iOldPlayer.__getDataPool().getIntDataMap()));
                iNewPlayer.__getDataPool().setFloatDataMap(new HashMap<>(iOldPlayer.__getDataPool().getFloatDataMap()));
                iNewPlayer.__getDataPool().setDoubleDataMap(new HashMap<>(iOldPlayer.__getDataPool().getDoubleDataMap()));
                iNewPlayer.__getDataPool().setBooleanDataMap(new HashMap<>(iOldPlayer.__getDataPool().getBooleanDataMap()));
            }
        });
    }
}