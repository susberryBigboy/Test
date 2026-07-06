package com.papack.test;

import com.papack.test.variable.DataPool;
import com.papack.test.variable.FieldType;

//Define the setter and getter for the data you want to add.
public interface IModPropertiesServerPlayer {
    DataPool test$getDataPool();

    void test$setCustomData(FieldType fieldType, Object value);

    Object test$getCustomData(FieldType fieldType);

}
