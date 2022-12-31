package com.dnd.botTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.Action;

public abstract class Action implements Serializable {

	private static final long serialVersionUID = 1L;

	protected long key;
	protected String name;
	protected String text;
	protected boolean mainAct;
	protected boolean mediator;
	private String[] backTo = new String[2];
	private List<Integer> actCircle = new ArrayList<>();
	
	public Action beckKey(String string)
	{
		this.backTo[0] = string;
		return this;
	}
	
	public Action beckCall(String string)
	{
		if(this.backTo[0] != null)
		{
			this.backTo[1] = string;
		}
		return this;
	}
	
	boolean hasBack()
	{
		return backTo[0] != null;
	}
	
	String[] backTo()
	{
		return backTo;
	}

	
	protected abstract String[][] buildButtons();
	public abstract Action continueAction(String key);
	protected abstract boolean hasButtons();

	public long getKey()
	{
		return key;
	}
	
	List<Integer> end()
	{
		return actCircle;
	}

	void toCircle(Integer act) 
	{
		this.actCircle.add(act);
	}

	public String toString()
	{
		return "  |" + name + "|  ";

	}
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Action setMediator() 
	{
		this.mediator = true;
		return this;
	}

}
