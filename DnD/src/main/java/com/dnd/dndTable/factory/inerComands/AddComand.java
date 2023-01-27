package com.dnd.dndTable.factory.inerComands;

import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonTypeName;

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
