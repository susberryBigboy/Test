package com.papack.test.variable;

import com.mojang.serialization.Codec;

import java.util.Map;

public class Codecs {
    public static final Codec<Map<String, Integer>> INT_MAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.INT);
    public static final Codec<Map<String, String>> STRING_MAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING);
    public static final Codec<Map<String, Float>> FLOAT_MAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.FLOAT);
    public static final String INT_MAP_NAME = "int_map";
    public static final String STRING_MAP_NAME = "string_map";
    public static final String FLOAT_MAP_NAME = "float_map";
}
