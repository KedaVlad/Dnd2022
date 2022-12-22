package com.dnd.dndTable.creatingDndObject.workmanship.features;

import java.io.Serializable;
import java.util.List;

import com.dnd.botTable.Action;
import com.dnd.botTable.actions.HeroAction;
import com.dnd.dndTable.ActionObject;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor;
import com.dnd.dndTable.creatingDndObject.bagDnd.Pack;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.factory.inerComands.InerComand;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "FEATURE")
@JsonSubTypes({
	@JsonSubTypes.Type(value = ActiveFeature.class, name = "ACTIVE_FEATURE"),
	@JsonSubTypes.Type(value = InerFeature.class, name = "INER_FEATURE"),
	@JsonSubTypes.Type(value = PassiveFeature.class, name = "PASSIVE_FEATURE")})
public class Feature implements Serializable, ActionObject, ObjectDnd 
{

	private static final long serialVersionUID = 5053270361827778941L;

	private String name;
	private String description;
	private For depend;

	enum For 
	{
		BATTLE, SPELL, OTHER
	}

	public Action makeAction()
	{
		return HeroAction.create(getName(), 000034234, getDescription(), null);
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

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return feature;
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
