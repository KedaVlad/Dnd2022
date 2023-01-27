package com.dnd.dndTable.creatingDndObject.workmanship.features;

import java.io.Serializable;

import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "FEATURE")
@JsonSubTypes({
	@JsonSubTypes.Type(value = ActiveFeature.class, name = "ACTIVE_FEATURE"),
	@JsonSubTypes.Type(value = InerFeature.class, name = "INER_FEATURE"),
	@JsonSubTypes.Type(value = PassiveFeature.class, name = "PASSIVE_FEATURE")})
public class Feature implements Serializable, ObjectDnd 
{

	private static final long serialVersionUID = 5053270361827778941L;

	private String name;
	private String description;
	private For depend;

	enum For 
	{
		BATTLE, SPELL, OTHER
	}

	public String toString() 
	{
		return getName() + " - "+ getDescription();
	}

	public For getDepend() {
		return depend;
	}

	public void setDepend(For depend) {
		this.depend = depend;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Feature build()
	{
		return new Feature();
	}
	
	public Feature name(String name)
	{
		this.name = name;
		return this;
	}
	
	public Feature description(String description)
	{
		this.description = description;
		return this;
	}
	

	public Feature depend(For depend)
	{
		this.depend = depend;
		return this;
	}
	
}
