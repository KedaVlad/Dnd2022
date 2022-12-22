package com.dnd.dndTable.creatingDndObject.bagDnd;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("PACK")
public class Pack extends Items 
{
private static final long serialVersionUID = 1L;

public static Pack create(String name)
{
	Pack answer = new Pack();
	answer.setName(name);
	
	return answer;
}
}
