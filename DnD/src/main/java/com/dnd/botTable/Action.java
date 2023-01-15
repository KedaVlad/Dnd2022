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
	boolean replyBreaker;
	boolean replyButtons;
	private String[] backTo = new String[2];
	private List<Integer> actCircle = new ArrayList<>();

	public Action replyButtons()
	{
		this.replyBreaker = false;
		this.replyButtons = true;
		return this;
	}
	
	public Action returnTo(String string)
	{
		this.backTo[0] = string;
		return this;
	}

	public Action returnTo(String nameAction, String callBack)
	{
		this.backTo[0] = nameAction;
		this.backTo[1] = callBack;
		return this;
	}

	public Action replyEnd()
	{
		this.replyBreaker = true;
		this.replyButtons = false;
		return this;
	}

	public boolean replyContain(String string)
	{
		for(String[] line: buildButtons())
		{
			for(String target: line)
			{
				if(target.equals(string)) return true;
			}
		}
		return false;
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
		List<Integer> end = new ArrayList<>();
		end.addAll(actCircle);
		actCircle.clear();
		return end;
	}
	
	Integer getMessageId()
	{
		return actCircle.get(0);
	}

	void toCircle(Integer act) 
	{
		this.actCircle.add(act);
	}

	public String toString()
	{
		String trash = "";
		for(Integer i:  actCircle)
		{
			trash += i + ", ";
		}
		return "  |" + name + "["+ trash + "] " + "|  ";
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setMainAct(boolean mainAct)
	{
		this.mainAct = mainAct;
	}
	
	public void setText(String text)
	{
		this.text = text;
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

	public boolean isReplyBreaker() 
{
		return replyBreaker;
	}

	public boolean isReplyButtons() 
{
		return replyButtons;
	}

	
}
