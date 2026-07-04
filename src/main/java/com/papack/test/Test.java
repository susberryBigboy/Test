package com.papack.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import org.joml.Math;

public class Test implements ModInitializer {

    public static final String MOD_ID = "testmod";

    @Override
    public void onInitialize() {

        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {

            if (entity instanceof IModPropertiesServerPlayer iPlayer) {

            int value = iPlayer.test$getCustomIntData();
            int modifiedValue = Math.max(0,value - 5);

            iPlayer.test$setCustomIntData(modifiedValue);
            }

            return true;
        });
    }
}
