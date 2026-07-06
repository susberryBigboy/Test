package com.papack.test;

import com.papack.test.fields.DataPool;
import com.papack.test.fields.FieldType;

//Define the setter and getter for the data you want to add.
public interface IModPropertiesServerPlayer {
    DataPool $_getDataPool();

    void $_setPoolData(FieldType fieldType, Object value);

    Object $_getPoolData(FieldType fieldType);

}
