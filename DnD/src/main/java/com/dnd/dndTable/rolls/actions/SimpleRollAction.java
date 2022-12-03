package com.dnd.dndTable.rolls.actions;

import com.dnd.dndTable.rolls.Dice;

public class SimpleRollAction extends HeroAction
{
	private static final long serialVersionUID = 1L;
	private Dice base;

	public Dice getBase() 
	{
		return base;
	}

	public void setBase(Dice base) 
	{
		this.base = base;
	}
}
