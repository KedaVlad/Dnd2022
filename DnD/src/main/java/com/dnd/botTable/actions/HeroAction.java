package com.dnd.botTable.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.Action;

public class HeroAction extends Action
{
	private static final long serialVersionUID = 1L;

	private List<HeroAction> nextStep = new ArrayList<>();

	public List<HeroAction> getNextStep() 
	{
		return nextStep;
	}

	public void setNextStep(List<HeroAction> nextStep) 
	{
		this.nextStep = nextStep;
	}

	public boolean isLastStep()
	{
		return nextStep == null || nextStep.isEmpty();
	}
	
	public static HeroAction create(String name, long key, String text, List<HeroAction> nextStep)
	{
		HeroAction answer = new HeroAction();
		answer.name = name;
		answer.key = key;
		answer.mainAct = true;
		answer.mediator = false;
		answer.text = text;
		answer.nextStep = nextStep;
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
		
		for(HeroAction action: this.nextStep)
		{
			if(action.name.equals(name))
			{
				return action;
			}
		}
		return null;
	}

	@Override
	public String[][] buildButtons()
	{
		String[][] buttoms = null;
		if(this.getNextStep() != null && this.getNextStep().size() != 0)
		{
		buttoms = new String[this.getNextStep().size()][1];
		
		for(int i = 0; i < this.getNextStep().size(); i++)
		{
			buttoms[i][0] = this.getNextStep().get(i).name;
		}
		}
		
		return buttoms;
	}

	@Override
	protected boolean hasButtons() {
		// TODO Auto-generated method stub
		return nextStep != null && !nextStep.isEmpty();
	}
	
}
