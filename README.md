# Multiple field settings

## Register the field

**example:**  
`public static final FieldType lifeCounter = new FieldType("life_counter", FieldType.Type.INT);`

Write it in **\[Fields.class\]** like this.  
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
`iPlayer.getDataPool().setValue(lifeCounter, value);`

## Reading from the Field
Cast ServerPlayer to IModPropertiesServerPlayer.  
example:  
`int currentValue = (int) iPlayer.$_getPoolData(lifeCounter);`
Please cast `ServerPlayer` to `IModPropertiesServerPlayer`.
In this example, the **received value is an "Object"**, so cast it to **"\(int\)"**.
For other types, cast accordingly.
