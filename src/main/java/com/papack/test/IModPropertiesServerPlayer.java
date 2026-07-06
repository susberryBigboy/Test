package com.papack.test;

import com.papack.test.fields.DataPool;
import com.papack.test.fields.FieldType;

//Define the setter and getter for the data you want to add.
public interface IModPropertiesServerPlayer {
    DataPool __getDataPool();

    void __setPoolData(FieldType fieldType, Object value);

    Object __getPoolData(FieldType fieldType);
}
