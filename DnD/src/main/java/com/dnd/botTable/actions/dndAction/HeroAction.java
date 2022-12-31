package com.dnd.botTable.actions.dndAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.Action;

public class HeroAction extends DndAction
{
	private static final long serialVersionUID = 1L;

	private Action[][] nextStep;

	public Action[][] getNextStep() 
	{
		return nextStep;
	}

	public boolean isLastStep()
	{
		return nextStep == null || nextStep.length == 0;
	}
	
	public static HeroAction create(String name, long key, String text, Action[][] nextStep)
	{
		HeroAction answer = new HeroAction();
		answer.setName(name);
		answer.key = key;
		answer.mainAct = true;
		answer.text = text;
		answer.setNextStep(nextStep);
		return answer;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	
	@Override
	public Action continueAction(String name) {
		
		for(Action[] actions: this.nextStep)
		for(Action action: actions)
		{
			if(action.getName().equals(name))
			{
				return action;
			}
		}
		return null;
	}

	@Override
	public String[][] buildButtons()
	{
		String[][] buttons = new String[nextStep.length][];
		for(int i = 0; i < nextStep.length; i++)
		{
			buttons[i] = new String[nextStep[i].length];
			for(int j = 0; j < buttons[i].length; j++)
			{
				buttons[i][j] = nextStep[i][j].getName();
			}
		}
		
		return buttons;
	}

	@Override
	protected boolean hasButtons() {
		// TODO Auto-generated method stub
		return nextStep != null && nextStep.length > 0;
	}
	
	public String toString()
	{
		return "  | HERO ACTION :" + name + "|  ";
	}

	public void setNextStep(Action[][] nextStep) {
		this.nextStep = nextStep;
	}
	
	public HeroAction rebuild()
		{
			return HeroAction.create(this.name, this.key, this.text, null);
		}
	
}
