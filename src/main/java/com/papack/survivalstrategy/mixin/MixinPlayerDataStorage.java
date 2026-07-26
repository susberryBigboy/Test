package com.papack.survivalstrategy.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerDataStorage.class)
public class MixinPlayerDataStorage {

    @Inject(method = "load(Lnet/minecraft/server/players/NameAndId;)Ljava/util/Optional;", at = @At(value = "RETURN"))
    private void modifyPlayerData(NameAndId nameAndId, CallbackInfoReturnable<Optional<CompoundTag>> cir) {

        Optional<CompoundTag> opt = cir.getReturnValue();

        if (opt.isEmpty()) return;

        CompoundTag tag = opt.get();

        Optional<CompoundTag> booleanMap = tag.getCompound("survivalstrategy_boolean_map");

        boolean flagBan = booleanMap.flatMap(map -> map.getBoolean("flag_ban")).orElse(false);

        if (!flagBan) {
            return;
        }

        //SurvivalStrategy.LOGGER.info("flag BAN: {}", true);

        tag.putFloat("Health", 20.0F);

        tag.remove("Inventory");
        tag.remove("EnderItems");

        tag.putInt("foodLevel", 20);
        tag.putFloat("foodSaturationLevel", 5.0F);

        tag.putInt("XpLevel", 0);
        tag.putInt("XpTotal", 0);
        tag.putFloat("XpP", 0);

        tag.remove("SpawnX");
        tag.remove("SpawnY");
        tag.remove("SpawnZ");
        tag.remove("SpawnDimension");
        tag.remove("SpawnForced");
        tag.remove("Pos");
        tag.remove("LastDeathLocation");
    }
}