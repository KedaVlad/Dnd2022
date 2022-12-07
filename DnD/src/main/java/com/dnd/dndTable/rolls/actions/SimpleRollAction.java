package com.dnd.dndTable.rolls.actions;

import java.util.List;

import com.dnd.dndTable.rolls.Dice;

public class SimpleRollAction extends HeroAction
{
	public SimpleRollAction(String name, String text, List<HeroAction> nextStep) {
		super(name, text, nextStep);
		// TODO Auto-generated constructor stub
	}

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
