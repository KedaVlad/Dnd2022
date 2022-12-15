package com.dnd.botTable.actions;

import java.util.List;

import com.dnd.dndTable.rolls.Dice;

public class SimpleRollAction extends HeroAction
{
		
	private static final long serialVersionUID = 1L;
	
	public static SimpleRollAction create(String name, long key, String text, List<HeroAction> nextStep, List<Dice> base)
	{
		SimpleRollAction answer = new SimpleRollAction();
		answer.name = name;
		answer.key = key;
		answer.mainAct = true;
		answer.mediator = false;
		answer.text = text;
		answer.setNextStep(nextStep);
		answer.base = base;
		return answer;
	}
	
	private List<Dice> base;

	public List<Dice> getBase() 
	{
		return base;
	}

	public void setBase(List<Dice> base) 
	{
		this.base = base;
	}
}
