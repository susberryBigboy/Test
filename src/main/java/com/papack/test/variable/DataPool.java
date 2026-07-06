package com.papack.test.variable;

import java.util.HashMap;
import java.util.Map;

public class DataPool {

    private Map<String, Integer> intDataMap = new HashMap<>();
    private Map<String, String> stringDataMap = new HashMap<>();
    private Map<String, Float> floatDataMap = new HashMap<>();


    // GET
    private int getIntData(String fieldName) {
        return intDataMap.getOrDefault(fieldName, 0);
    }

    private String getStringData(String fieldName) {
        return stringDataMap.getOrDefault(fieldName, "");
    }

    private float getFloatData(String fieldName) {
        return floatDataMap.getOrDefault(fieldName, 0.0f);
    }


    // SET Value
    private void setIntData(String fieldName, int value) {
        intDataMap.put(fieldName, value);
    }

    private void setStringData(String fieldName, String value) {
        stringDataMap.put(fieldName, value);
    }

    private void setFloatData(String fieldName, float value) {
        floatDataMap.put(fieldName, value);
    }

    // SET Map
    public void setIntDataMap(Map<String, Integer> intDataMap) {
        this.intDataMap = intDataMap;
    }

    public void setStringDataMap(Map<String, String> stringDataMap) {
        this.stringDataMap = stringDataMap;
    }

    public void setFloatDataMap(Map<String, Float> floatDataMap) {
        this.floatDataMap = floatDataMap;
    }


    // GET MAP
    public Map<String, Integer> getIntDataMap() {
        return intDataMap;
    }

    public Map<String, String> getStringDataMap() {
        return stringDataMap;
    }

    public Map<String, Float> getFloatDataMap() {
        return floatDataMap;
    }


    // SET VALUES
    public void setValue(FieldType fieldType, Object value) {

        switch (fieldType.type()) {
            case INT -> setIntData(fieldType.fieldName(), (Integer) value);
            case STRING -> setStringData(fieldType.fieldName(), (String) value);
            case FLOAT -> setFloatData(fieldType.fieldName(), (Float) value);
        }
    }

    public Object getValue(FieldType fieldType) {

        return switch (fieldType.type()) {
            case INT -> getIntData(fieldType.fieldName());
            case STRING -> getStringData(fieldType.fieldName());
            case FLOAT -> getFloatData(fieldType.fieldName());
        };
    }
}
