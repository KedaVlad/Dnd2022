package com.dnd.dndTable.factory.inerComands;

import com.dnd.dndTable.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("ADD_COMAND")
@JsonDeserialize(using = InerComadDeserializer.class)
public class AddComand 
{
	ObjectDnd target;
	
}
