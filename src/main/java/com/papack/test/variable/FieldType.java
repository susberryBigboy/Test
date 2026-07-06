package com.papack.test.variable;

public record FieldType(String fieldName, Type type) {
    public enum Type {
        INT,
        STRING,
        FLOAT
    }
}
