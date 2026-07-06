package com.papack.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

import java.util.HashMap;

import static com.papack.test.variable.Fields.lifeCounter;

public class Test implements ModInitializer {

    public static final String MOD_ID = "testmod";

    @Override
    public void onInitialize() {

        // Value modification upon player death
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {

            if (entity instanceof IModPropertiesServerPlayer iPlayer) {

                int value = (int) iPlayer.test$getCustomData(lifeCounter);
                // 🔴 java.lang.Math.max が自動で使われるよう、JOMLのインポートを削除しました
                int modifiedValue = Math.max(0, value - 5);

                iPlayer.test$setCustomData(lifeCounter, modifiedValue);
            }

            return true;
        });

        // Carry over data upon respawning.
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {

            if (oldPlayer instanceof IModPropertiesServerPlayer iOldPlayer
                    && newPlayer instanceof IModPropertiesServerPlayer iNewPlayer) {

                // 🔴 データの参照共有によるバニラ/MODの競合バグを防ぐため、new HashMap<> でコピーを作成してセットします
                iNewPlayer.test$getDataPool().setIntDataMap(new HashMap<>(iOldPlayer.test$getDataPool().getIntDataMap()));
                iNewPlayer.test$getDataPool().setStringDataMap(new HashMap<>(iOldPlayer.test$getDataPool().getStringDataMap()));
                iNewPlayer.test$getDataPool().setFloatDataMap(new HashMap<>(iOldPlayer.test$getDataPool().getFloatDataMap()));
            }
        });
    }
}