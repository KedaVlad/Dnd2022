package com.dnd.botTable.actions.factoryAction;

import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.Action;

public class FactoryAction extends Action
{
	private static final long serialVersionUID = 1L;

	protected String[][] nextStep;
	private List<String> localData = new ArrayList<>();

	public static FactoryAction create(String name, long key, boolean mediator, String text, String[][] nextStep)
	{
		FactoryAction answer = new FactoryAction();
		answer.setName(name);
		answer.key = key;
		answer.mainAct = true;
		answer.mediator = mediator;
		answer.text = text;
		answer.nextStep = nextStep;
		return answer;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}

	public void setMediator(boolean mediator)
	{
		this.mediator = mediator;
	}
	
	public long getKey()
	{
		return key;
	}

	public List<String> getLocalData() 
	{
		return localData;
	}
	
	public void setLocalData(List<String> localData) 
	{
		this.localData = localData;
	}

	public String[][] getNextStep() 
	{
		return nextStep;
	}

	public void setNextStep(String[][] nextStep) 
	{
		this.nextStep = nextStep;
	}

	@Override
	public FactoryAction continueAction(String name)
	{
		FactoryAction action = new FactoryAction();
		action.key = this.key;
		action.mainAct = true;
		action.localData.addAll(this.getLocalData());
		action.localData.add(name);
		return action;
	}
	
	@Override
	protected String[][] buildButtons() 
	{
		return nextStep;
	}
	
	@Override
	protected boolean hasButtons() 
	{
		return (nextStep != null) && (nextStep.length != 0);
	}

	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	public String toString()
	{
		String localData = "local data - ";
		for(String string: this.localData)
		{
			localData += string + " ";
		}
		return "  | FACTORY ACTION :" + name + localData + "|  ";
	}
}
