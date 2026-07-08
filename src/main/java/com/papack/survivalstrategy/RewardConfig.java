package com.papack.survivalstrategy;

public class RewardConfig {
/*
    public static final int DAY_4 = 96000;
    public static final int DAY_2 = 48000;
    public static final int DAY_1 = 24000;

    public static final int HOUR_4 = 4000;
    public static final int HOUR_1 = 1000;

    public static final int MINUTES_50 = 834;
    public static final int MINUTES_40 = 667;
    public static final int MINUTES_30 = 500;
    public static final int MINUTES_20 = 334;
    public static final int MINUTES_10 = 167;
    public static final int MINUTES_5 = 84;
*/

    public static int getGameTime(int day, int hour, int minute) {
       return (day * 24000) + (hour * 1000) + (1000 / 60 * minute);
    }
}
