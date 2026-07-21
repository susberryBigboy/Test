package com.papack.survivalstrategy.mixin;

import com.papack.survivalstrategy.PlayerDataManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.papack.survivalstrategy.SurvivalStrategy.config;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "die", at = @At(value = "HEAD"))
    private void whoKilledMob(DamageSource source, CallbackInfo ci) {

        LivingEntity entity = (LivingEntity) (Object) this;

        if (source.getEntity() instanceof ServerPlayer sourcePlayer) {

            PlayerDataManager.onEntityKill(entity, sourcePlayer);
        }
    }

    // Item Drop On Death
    @Inject(method = "dropAllDeathLoot", at = @At(value = "HEAD"), cancellable = true)
    private void dropItemUponDeath(ServerLevel serverLevel, DamageSource damageSource, CallbackInfo ci) {

        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof ServerPlayer deadPlayer) {

            // Killed By Another Player
            if (damageSource.getEntity() instanceof ServerPlayer) {
                if (config.dropInventoryWhenKilledByPlayer) {
                    deadPlayer.getInventory().clearContent();
                    ci.cancel();
                }
            }

            // In the event of death due to other causes
            if (!config.dropInventoryUponDeath) {
                deadPlayer.getInventory().clearContent();
                ci.cancel();
            }
        }
    }
}