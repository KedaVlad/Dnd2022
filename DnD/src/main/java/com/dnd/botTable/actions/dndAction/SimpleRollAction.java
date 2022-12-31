package com.dnd.botTable.actions.dndAction;

import java.util.List;

import com.dnd.botTable.Action;
import com.dnd.dndTable.rolls.Dice;

public class SimpleRollAction extends HeroAction
{
		
	private static final long serialVersionUID = 1L;
	
	public static SimpleRollAction create(String name, long key, String text, Action[][] nextStep, List<Dice> base)
	{
		SimpleRollAction answer = new SimpleRollAction();
		answer.setName(name);
		answer.key = key;
		answer.mainAct = true;
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
