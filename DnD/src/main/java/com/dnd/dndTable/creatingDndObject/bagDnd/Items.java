package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.io.Serializable;

import com.dnd.Source;
import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "ITEM")
@JsonSubTypes({
	@JsonSubTypes.Type(value = Armor.class, name = "ARMOR"),
	@JsonSubTypes.Type(value = Pack.class, name = "PACK"),
	@JsonSubTypes.Type(value = Pack.class, name = "AMMUNITION"),
	@JsonSubTypes.Type(value = Pack.class, name = "TOOL"),
	@JsonSubTypes.Type(value = Weapon.class, name = "WEAPON")})
public class Items implements Serializable, ObjectDnd, Source{
	
	private static final long serialVersionUID = -1353539867889183740L;
	
	private String name;
	private String description;
	private boolean used;
	
	public Items() {}

	
	public String getName() {
		return name;
	}
	public String toString() {
		return getName();
	}

	@Override
	public String source()
	{
		return null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean isUsed() {
		return used;
	}


	public void setUsed(boolean used) {
		this.used = used;
	}
}
