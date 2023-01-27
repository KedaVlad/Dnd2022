package com.dnd.botTable.actions;

import java.io.Serializable;

import com.dnd.botTable.actions.Action.Location;

public abstract class BaseAction implements Serializable
{
	private static final long serialVersionUID = 1L;
	Location location;
	boolean mediator;
	boolean replyButtons;
	boolean cloud;
	long key;
	String name;

	public abstract String[][] buildButtons();

	public abstract BaseAction continueAction(String key);

	public abstract boolean hasButtons();

	public abstract boolean replyContain(String string);

	public Location getLocation() 
	{
		return location;
	}

	public long getKey() 
	{
		return key;
	}

	public String getName() 
	{
		return name;
	}


	public boolean isMediator() 
	{
		return mediator;
	}


	public void setMediator() 
	{
		this.mediator = true;
	}

	public boolean isReplyButtons() 
	{
		return replyButtons;
	}

	public void setReplyButtons() 
	{
		this.replyButtons = true;
	}

	public boolean isCloud() {
		return cloud;
	}

	public void setCloud(boolean cloud) {
		this.cloud = cloud;
	}

}
