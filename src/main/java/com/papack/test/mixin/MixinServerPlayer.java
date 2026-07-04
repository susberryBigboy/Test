package com.papack.test.mixin;

import com.papack.test.IModPropertiesServerPlayer;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer implements IModPropertiesServerPlayer {

    @Unique
    private int customInt = -1;

    @Override
    public int test$getCustomIntData() {
        return customInt;
    }

    @Override
    public void test$setCustomIntData(int value) {
        customInt = value;
    }
}
