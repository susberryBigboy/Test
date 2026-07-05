package com.papack.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import org.joml.Math;

public class Test implements ModInitializer {

    public static final String MOD_ID = "testmod";

    @Override
    public void onInitialize() {

        // Value modification upon player death
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {

            if (entity instanceof IModPropertiesServerPlayer iPlayer) {

                int value = iPlayer.test$getCustomIntData();
                int modifiedValue = Math.max(0, value - 5);

                iPlayer.test$setCustomIntData(modifiedValue);
            }

            return true;
        });

        // Carry over data upon respawning.
        // Do not modify the values here.
        // This is because this section is invoked when the player dies or returns from the End.
        // If you wish to change the values upon death, please use an event to do so.
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {

            if (oldPlayer instanceof IModPropertiesServerPlayer iOldPlayer
                    && newPlayer instanceof IModPropertiesServerPlayer iNewPlayer) {

                iNewPlayer.test$setCustomIntData(iOldPlayer.test$getCustomIntData());
            }
        });
    }
}
