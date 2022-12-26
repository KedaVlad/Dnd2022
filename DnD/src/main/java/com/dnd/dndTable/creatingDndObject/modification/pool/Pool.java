package com.dnd.dndTable.creatingDndObject.modification.pool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.bagDnd.Armor;
import com.dnd.dndTable.creatingDndObject.bagDnd.Pack;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "POOL")
@JsonSubTypes({
	@JsonSubTypes.Type(value = SoftPool.class, name = "SOFT_POOL"),
	@JsonSubTypes.Type(value = TimePool.class, name = "TIME_POOL"),
	@JsonSubTypes.Type(value = SimplePool.class, name = "SIMPLE_POOL")})
public abstract class Pool<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected List<T> active = new ArrayList<>();
	
	public List<T> getActive() 
	{
		return active;
	}

	public void setActive(List<T> active)
	{
		this.active = active;
	}
}
