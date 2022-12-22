package com.dnd.dndTable.factory.inerComands;

import com.dnd.dndTable.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeName("ADD_COMAND")
public class AddComand extends InerComand
{
	private static final long serialVersionUID = 1L;
	
	public static AddComand create(ObjectDnd... object)
	{
		AddComand answer = new AddComand();
		answer.target = object;
		return answer;
	}
	
	
	private ObjectDnd[] target;

	public ObjectDnd[] getTarget() 
	{
		return target;
	}
	
	
}
