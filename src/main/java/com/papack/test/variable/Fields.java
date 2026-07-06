package com.papack.test.variable;

public class Fields {
    public static final FieldType lifeCounter = new FieldType("life_counter", FieldType.Type.INT);
    public static final FieldType testIntField = new  FieldType("test_int", FieldType.Type.INT);

    public static final FieldType lastMob = new FieldType("last_mob", FieldType.Type.STRING);
    public static final FieldType testStrField = new FieldType("test_string", FieldType.Type.STRING);

    public static final FieldType lastReward = new FieldType("last_reward", FieldType.Type.FLOAT);
    public static final FieldType testFloatField = new FieldType("test_float", FieldType.Type.FLOAT);
}
