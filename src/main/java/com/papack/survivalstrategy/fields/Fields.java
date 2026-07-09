package com.papack.survivalstrategy.fields;

public class Fields {

    // Remaining Time - Remaining playtime
    public static final FieldType remainingTime = new FieldType("remaining_time", FieldType.Type.INT);

    // Survival Time - Total time spent in the world
    public static final FieldType survivalTime = new FieldType("survival_time", FieldType.Type.INT);

    // Registered Player - Determine whether the player has already participated.
    public static final FieldType registeredPlayer = new FieldType("registered_player", FieldType.Type.BOOLEAN);

    // Flag Ban -  Flag indicating that the "BAN" process has been initiated.
    public static final FieldType flagBan = new FieldType("flag_ban", FieldType.Type.BOOLEAN);

    // test
    /*
    public static final FieldType lastMob = new FieldType("last_mob", FieldType.Type.STRING);
    public static final FieldType testStrField = new FieldType("test_string", FieldType.Type.STRING);
    public static final FieldType lastReward = new FieldType("last_reward", FieldType.Type.FLOAT);
    public static final FieldType testFloatField = new FieldType("test_float", FieldType.Type.FLOAT);


    public static final FieldType testDoubleField = new FieldType("test_double", FieldType.Type.DOUBLE);
    public static final FieldType testBooleanField = new FieldType("test_boolean", FieldType.Type.BOOLEAN);
    */

}
