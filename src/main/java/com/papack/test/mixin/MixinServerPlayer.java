package com.papack.test.mixin;

import com.papack.test.IModPropertiesServerPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer implements IModPropertiesServerPlayer {

    @Unique
    // Default value
    private int customInt = 0;

    @Override
    public int test$getCustomIntData() {
        return customInt;
    }

    @Override
    public void test$setCustomIntData(int value) {
        customInt = value;
    }

    // ------------------------------------------------------------------------------
    // Carry over data upon respawning.
    // Do not modify the values here.
    // This is because this section is invoked when the player dies or returns from the End.
    // If you wish to change the values upon death, please use an event to do so.

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void onCopyFrom(ServerPlayer serverPlayer, boolean bl, CallbackInfo ci) {
        // Cast from serverPlayer (old instance) to retrieve the data.
        IModPropertiesServerPlayer oldData = (IModPropertiesServerPlayer) serverPlayer;

        // Pass the value to `this` (the newly created current instance).
        this.test$setCustomIntData(oldData.test$getCustomIntData());
    }
    // ------------------------------------------------------------------------------

    @Unique
    // Name to assign to NBT data.
    // If there are multiple values, you need to set them individually.
    private final String DATA_FIELD_NAME = "custom_int";

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    private void readAdditionalSaveData(ValueInput valueInput, CallbackInfo ci) {

        Player player = (Player) (Object) this;
        // Please follow the implementation of the methods in the target Mixin when writing your code.
        ((IModPropertiesServerPlayer) player).test$setCustomIntData(valueInput.getIntOr(DATA_FIELD_NAME, 0));
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    private void addAdditionalSaveData(ValueOutput valueOutput, CallbackInfo ci) {

        Player player = (Player) (Object) this;
        // Please follow the implementation of the methods in the target Mixin when writing your code.
        valueOutput.putInt(DATA_FIELD_NAME, ((IModPropertiesServerPlayer) player).test$getCustomIntData());
    }
}
