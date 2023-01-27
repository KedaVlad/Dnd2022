package com.dnd.botTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.actions.BaseAction;

public class Act implements Serializable
{
	private static final long serialVersionUID = 1L;
	String name;
	String text;
	BaseAction action;
	String returnTarget;
	String returnCall;
	List<Integer> actCircle;
	
	Act(){}
	
	public static ActBuilder builder()
	{
		return new ActBuilder();
	}

	List<Integer> end()
	{
		List<Integer> end = new ArrayList<>();
		end.addAll(actCircle);
		actCircle.clear();
		return end;
	}

	void toCircle(Integer act) 
	{
		this.actCircle.add(act);
	}
	
	boolean hasAction()
	{
		return action != null;
	}
	
	boolean hasMediator()
	{
		return action != null && action.isMediator();
	}
		
	boolean hasReply(String string)
	{
		return action != null && action.replyContain(string);
	}
	
	boolean hasCloud()
	{
		return action != null && action.isCloud();
	}
	
	boolean hasBack()
	{
		return returnTarget != null;
	}

	public BaseAction getAction() 
	{
		return action;
	}
}

