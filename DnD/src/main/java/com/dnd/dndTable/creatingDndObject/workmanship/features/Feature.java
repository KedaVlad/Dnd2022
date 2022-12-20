package com.dnd.dndTable.creatingDndObject.workmanship.features;

import java.io.Serializable;
import java.util.List;

import com.dnd.botTable.Action;
import com.dnd.botTable.actions.HeroAction;
import com.dnd.dndTable.ActionObject;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.factory.inerComands.InerComand;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Feature implements Serializable, ActionObject
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

	public static Feature create(String name, String text)
	{
		Feature answer = new Feature();
		answer.name = name;
		answer.description = text;
		return answer;
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
}
