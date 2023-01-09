package com.dnd.botTable.actions.dndAction;

import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.Action;
import com.dnd.dndTable.rolls.Dice;

public class SimpleRollAction extends DndAction
{
		
	private static final long serialVersionUID = 1L;
	
	public static SimpleRollAction create(String name, long key, String text, List<Dice> base)
	{
		SimpleRollAction answer = new SimpleRollAction();
		answer.setName(name);
		answer.key = key;
		answer.mainAct = true;
		answer.text = text;
		answer.base = base;
		return answer;
	}
	private List<Dice> base = new ArrayList<>();

	public List<Dice> getBase() 
	{
		return base;
	}
	
	public SimpleRollAction diceCombo(List<Dice> dices)
	{
		this.base = dices;
		return this;
	}
	
	public SimpleRollAction diceCombo(Dice...dices)
	{
		base = new ArrayList<>();
		for(Dice dice: dices)
		{
			base.add(dice);
		}
		return this;
	}
	
	public void setBase(List<Dice> base) 
	{
		this.base = base;
	}

	@Override
	protected String[][] buildButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action continueAction(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean hasButtons() {
		// TODO Auto-generated method stub
		return false;
	}
}
