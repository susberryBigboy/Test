package com.papack.test.mixin;

import com.papack.test.IModPropertiesServerPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.SERVER)
@Mixin(Player.class)
public class MixinPlayer {

    @Unique
    private final String DATA_FIELD_NAME = "custom_int";

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    private void readAdditionalSaveData(ValueInput valueInput, CallbackInfo ci) {

        Player player = (Player) (Object) this;
        ((IModPropertiesServerPlayer) player).test$setCustomIntData(valueInput.getIntOr(DATA_FIELD_NAME, 5));
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    private void addAdditionalSaveData(ValueOutput valueOutput, CallbackInfo ci) {

        Player player = (Player) (Object) this;
        valueOutput.putInt(DATA_FIELD_NAME, ((IModPropertiesServerPlayer) player).test$getCustomIntData());
    }
}
