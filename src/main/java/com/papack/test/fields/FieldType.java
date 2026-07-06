package com.papack.test.fields;

public record FieldType(String fieldName, Type type) {
    public enum Type {
        STRING,
        INT,
        FLOAT,
        DOUBLE,
        BOOLEAN
    }
}
