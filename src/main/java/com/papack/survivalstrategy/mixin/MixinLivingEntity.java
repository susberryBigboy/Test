package com.papack.survivalstrategy.mixin;

import com.papack.survivalstrategy.PlayerDataManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "die", at = @At(value = "HEAD"))
    private void whoKilledMob(DamageSource source, CallbackInfo ci) {

        LivingEntity entity = (LivingEntity) (Object) this;

        if (source.getEntity() instanceof ServerPlayer sourcePlayer) {

            PlayerDataManager.onEntityKill(entity, sourcePlayer);
        }
    }
}
