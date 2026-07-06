package com.papack.test.fields;

import com.mojang.serialization.Codec;

import java.util.Map;

import static com.papack.test.Test.MOD_ID;

public class Codecs {
    public static final Codec<Map<String, String>> STRING_MAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING);
    public static final Codec<Map<String, Integer>> INT_MAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.INT);
    public static final Codec<Map<String, Float>> FLOAT_MAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.FLOAT);
    public static final Codec<Map<String, Double>> DOUBLE_MAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.DOUBLE);
    public static final Codec<Map<String, Boolean>> BOOLEAN_MAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.BOOL);

    public static final String STRING_MAP_NAME = MOD_ID + "_string_map";
    public static final String INT_MAP_NAME = MOD_ID + "_int_map";
    public static final String FLOAT_MAP_NAME = MOD_ID + "_float_map";
    public static final String DOUBLE_MAP_NAME = MOD_ID + "_double_map";
    public static final String BOOLEAN_MAP_NAME = MOD_ID + "_boolean_map";
}
