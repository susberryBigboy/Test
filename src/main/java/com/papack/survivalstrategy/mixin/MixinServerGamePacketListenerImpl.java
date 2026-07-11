package com.papack.survivalstrategy.mixin;

import com.papack.survivalstrategy.IModPropertiesServerPlayer;
import com.papack.survivalstrategy.fields.Fields;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinServerGamePacketListenerImpl {

    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleMovePlayer", at = @At(value = "HEAD"), cancellable = true)
    private void dontMove(ServerboundMovePlayerPacket serverboundMovePlayerPacket, CallbackInfo ci) {

        // Movement during the ban is ignored.
        if (player instanceof IModPropertiesServerPlayer iPlayer) {
            if ((boolean) iPlayer.$_getPoolData(Fields.flagBan)) {
                ci.cancel();
            }
        }
    }
}