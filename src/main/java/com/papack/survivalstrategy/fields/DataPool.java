package com.papack.survivalstrategy.fields;

import java.util.HashMap;
import java.util.Map;

public class DataPool {

    private Map<String, String> stringDataMap = new HashMap<>();
    private Map<String, Integer> intDataMap = new HashMap<>();
    private Map<String, Float> floatDataMap = new HashMap<>();
    private Map<String, Double> doubleDataMap = new HashMap<>();
    private Map<String, Boolean> booleanDataMap = new HashMap<>();


    // GET
    private String getStringData(String fieldName) {
        return stringDataMap.getOrDefault(fieldName, "");
    }

    private int getIntData(String fieldName) {
        return intDataMap.getOrDefault(fieldName, 0);
    }

    private float getFloatData(String fieldName) {
        return floatDataMap.getOrDefault(fieldName, 0.0f);
    }

    private double getDoubleData(String fieldName) {
        return doubleDataMap.getOrDefault(fieldName, 0.0);
    }

    private Boolean getBooleanData(String fieldName) {
        return booleanDataMap.getOrDefault(fieldName, false);
    }


    // SET Value
    private void setStringData(String fieldName, String value) {
        stringDataMap.put(fieldName, value);
    }

    private void setIntData(String fieldName, int value) {
        intDataMap.put(fieldName, value);
    }

    private void setFloatData(String fieldName, float value) {
        floatDataMap.put(fieldName, value);
    }

    private void setDoubleData(String fieldName, double value) {
        doubleDataMap.put(fieldName, value);
    }

    private void setBooleanData(String fieldName, boolean value) {
        booleanDataMap.put(fieldName, value);
    }

    // SET Map
    public void setStringDataMap(Map<String, String> stringDataMap) {
        this.stringDataMap = stringDataMap;
    }

    public void setIntDataMap(Map<String, Integer> intDataMap) {
        this.intDataMap = intDataMap;
    }


    public void setFloatDataMap(Map<String, Float> floatDataMap) {
        this.floatDataMap = floatDataMap;
    }

    public void setDoubleDataMap(Map<String, Double> doubleDataMap) {
        this.doubleDataMap = doubleDataMap;
    }

    public void setBooleanDataMap(Map<String, Boolean> booleanDataMap) {
        this.booleanDataMap = booleanDataMap;
    }


    // GET MAP
    public Map<String, String> getStringDataMap() {
        return stringDataMap;
    }

    public Map<String, Integer> getIntDataMap() {
        return intDataMap;
    }

    public Map<String, Float> getFloatDataMap() {
        return floatDataMap;
    }

    public Map<String, Double> getDoubleDataMap() {
        return doubleDataMap;
    }

    public Map<String, Boolean> getBooleanDataMap() {
        return booleanDataMap;
    }

    // SET VALUES
    public void setValue(FieldType fieldType, Object value) {

        switch (fieldType.type()) {
            case STRING -> setStringData(fieldType.fieldName(), (String) value);
            case INT -> setIntData(fieldType.fieldName(), (Integer) value);
            case FLOAT -> setFloatData(fieldType.fieldName(), (Float) value);
            case DOUBLE -> setDoubleData(fieldType.fieldName(), (Double) value);
            case BOOLEAN -> setBooleanData(fieldType.fieldName(), (Boolean) value);
            default -> {
            }
        }
    }

    public Object getValue(FieldType fieldType) {

        return switch (fieldType.type()) {
            case STRING -> getStringData(fieldType.fieldName());
            case INT -> getIntData(fieldType.fieldName());
            case FLOAT -> getFloatData(fieldType.fieldName());
            case DOUBLE -> getDoubleData(fieldType.fieldName());
            case BOOLEAN -> getBooleanData(fieldType.fieldName());
        };
    }
}
