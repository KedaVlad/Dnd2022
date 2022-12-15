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
	private List<Integer> actCircle = new ArrayList<>();

	protected abstract String[][] buildButtons();
	protected abstract Action continueAction(String key);
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
		String answer = name + "|";

		for(Integer inter: actCircle)
		{
			answer += inter + "|";
		}
		return answer;
	}

}
