package com.papack.test.mixin;

import com.papack.test.IModPropertiesServerPlayer;
import com.papack.test.fields.DataPool;
import com.papack.test.fields.FieldType;
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

import static com.papack.test.fields.Codecs.*;


@Mixin(ServerPlayer.class)
public class MixinServerPlayer implements IModPropertiesServerPlayer {

    @Unique
    DataPool dataPool = new DataPool();

    @Override
    public DataPool $_getDataPool() {
        return dataPool;
    }

    @Override
    public void $_setPoolData(FieldType fieldType, Object value) {
        dataPool.setValue(fieldType, value);
    }

    @Override
    public Object $_getPoolData(FieldType fieldType) {
        return dataPool.getValue(fieldType);
    }


    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    private void readAdditionalSaveData(ValueInput valueInput, CallbackInfo ci) {

        // String Map
        Map<String, String> stringMap = valueInput.read(STRING_MAP_NAME, STRING_MAP_CODEC).orElse(new HashMap<>());
        dataPool.setStringDataMap(new HashMap<>(stringMap));

        // Int Map
        Map<String, Integer> intMap = valueInput.read(INT_MAP_NAME, INT_MAP_CODEC).orElse(new HashMap<>());
        dataPool.setIntDataMap(new HashMap<>(intMap));

        // Float Map
        Map<String, Float> floatMap = valueInput.read(FLOAT_MAP_NAME, FLOAT_MAP_CODEC).orElse(new HashMap<>());
        dataPool.setFloatDataMap(new HashMap<>(floatMap));

        // Double Map
        Map<String, Double> doubleMap = valueInput.read(DOUBLE_MAP_NAME, DOUBLE_MAP_CODEC).orElse(new HashMap<>());
        dataPool.setDoubleDataMap(new HashMap<>(doubleMap));

        // Boolean Map
        Map<String, Boolean> booleanMap = valueInput.read(BOOLEAN_MAP_NAME, BOOLEAN_MAP_CODEC).orElse(new HashMap<>());
        dataPool.setBooleanDataMap(new HashMap<>(booleanMap));
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    private void addAdditionalSaveData(ValueOutput valueOutput, CallbackInfo ci) {

        // String Map
        valueOutput.store(STRING_MAP_NAME, STRING_MAP_CODEC, dataPool.getStringDataMap());

        // Int Map
        valueOutput.store(INT_MAP_NAME, INT_MAP_CODEC, dataPool.getIntDataMap());

        // Float Map
        valueOutput.store(FLOAT_MAP_NAME, FLOAT_MAP_CODEC, dataPool.getFloatDataMap());

        // Double Map
        valueOutput.store(DOUBLE_MAP_NAME, DOUBLE_MAP_CODEC, dataPool.getDoubleDataMap());

        // Boolean Map
        valueOutput.store(BOOLEAN_MAP_NAME, BOOLEAN_MAP_CODEC, dataPool.getBooleanDataMap());
    }
}
