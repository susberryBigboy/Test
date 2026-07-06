package com.papack.test.mixin;

import com.papack.test.IModPropertiesServerPlayer;
import com.papack.test.variable.DataPool;
import com.papack.test.variable.FieldType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

import static com.papack.test.variable.Codecs.*;


@Mixin(ServerPlayer.class)
public class MixinServerPlayer implements IModPropertiesServerPlayer {

    @Unique
    DataPool dataPool = new DataPool();

    @Override
    public DataPool test$getDataPool() {
        return dataPool;
    }

    @Override
    public void test$setCustomData(FieldType fieldType, Object value) {
        dataPool.setValue(fieldType, value);
    }

    @Override
    public Object test$getCustomData(FieldType fieldType) {
        return dataPool.getValue(fieldType);
    }


    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    private void readAdditionalSaveData(ValueInput valueInput, CallbackInfo ci) {

        // Int Map
        Map<String, Integer> intMap = valueInput.read(INT_MAP_NAME, INT_MAP_CODEC).orElse(new HashMap<>());
        dataPool.setIntDataMap(new HashMap<>(intMap));

        // String Map
        Map<String, String> stringMap = valueInput.read(STRING_MAP_NAME, STRING_MAP_CODEC).orElse(new HashMap<>());
        dataPool.setStringDataMap(new HashMap<>(stringMap));

        // Float Map
        Map<String, Float> floatMap = valueInput.read(FLOAT_MAP_NAME, FLOAT_MAP_CODEC).orElse(new HashMap<>());
        dataPool.setFloatDataMap(new HashMap<>(floatMap));
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    private void addAdditionalSaveData(ValueOutput valueOutput, CallbackInfo ci) {

        // Int Map
        valueOutput.store(INT_MAP_NAME, INT_MAP_CODEC, dataPool.getIntDataMap());

        // String Map
        valueOutput.store(STRING_MAP_NAME, STRING_MAP_CODEC, dataPool.getStringDataMap());

        // Float Map
        valueOutput.store(FLOAT_MAP_NAME, FLOAT_MAP_CODEC, dataPool.getFloatDataMap());
    }
}
