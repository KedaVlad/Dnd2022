package com.dnd.dndTable.factory.inerComands;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "COMAND")
@JsonSubTypes({
	@JsonSubTypes.Type(value = AddComand.class, name = "ADD_COMAND"),
	@JsonSubTypes.Type(value = UpComand.class, name = "UP_COMAND"),
	@JsonSubTypes.Type(value = CloudComand.class, name = "CLOUD_COMAND")})

public abstract class InerComand implements Serializable 
{
	private static final long serialVersionUID = -5446546498879076199L;
}
