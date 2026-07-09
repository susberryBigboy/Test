# Multiple field settings

## Register the field

**example: \[ Field.class \]**  
`public static final FieldType lifeCounter = new FieldType("life_counter", FieldType.Type.INT);`

Write it in "Fields.class" like this.  
**"lifeCounter"** is the field name used in the code.  
**"life_counter"** is the tag used in NBT.  
Finally, specify the field type.  

You can register multiple fields.  

## Write to the field

Cast ServerPlayer to IModPropertiesServerPlayer.  
example:  
`if (serverPlayer instanceof IModPropertiesServerPlayer iPlayer){`  
`int value = 5;`  
`iPlayer.$_setPoolData(lifeCounter, value);`  
`}`

Writing it as follows also conveys the same meaning.  
`iPlayer.$_getDataPool().setValue(lifeCounter, value);`

## Reading from the Field
Cast ServerPlayer to IModPropertiesServerPlayer.  
example:  
`int currentValue = (int) iPlayer.$_getPoolData(lifeCounter);`  
Please cast **"ServerPlayer" to "IModPropertiesServerPlayer".**  
In this example, the **received value is an "Object"**, so cast it to **"\(int\)"**.
For other types, cast accordingly.


## Important

Each player is assigned a DataPool instance.  
The DataPool class contains maps for int, float, double, string, and boolean types.  
Since field types are predefined, you can use them like player properties without worrying about map operations.  
Be sure to cast the retrieved values to the required type.  

Each map is independent.  
By registering, you can use multiple fields.  
