package com.dnd.botTable.actions;

import com.dnd.botTable.Action;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class PreRoll extends HeroAction
{
	private static final long serialVersionUID = 1L;

	protected int status;
	protected String[][] nextStep = new String[][] {{"ADVENTURE", "BASIC", "DISADVENTURE"}};
	private RollAction action;
	private boolean criticalMiss;
	private boolean criticalHit;

	public static PreRoll create(RollAction action)
	{
		PreRoll answer = new PreRoll();
		answer.action = action;
		answer.getAction().getBase().add(0, new Dice("D20", 0, Roll.D20));
		answer.name = action.getName();
		answer.key = action.getKey();
		answer.mainAct = true;
		answer.mediator = false;
		answer.text = action.getText();
		return answer;
	}
	
	public int getStatus() 
	{
		return status;
	}
	@Override
	public Action continueAction(String name) {
		
		if(name == "ADVENTURE")
		{
			this.status = 1;
		}
		else if(name == "BASIC")
		{
			this.status = 2;
		}
		else if(name == "DISADVENTURE")
		{
			this.status = 3;
		}

		return this;
	}

	@Override
	public String[][] buildButtons()
	{
		return nextStep;
	}

	@Override
	protected boolean hasButtons() 
	{
		return (nextStep != null) && (nextStep.length != 0);
	}

	public boolean isCriticalMiss() 
	{
		return criticalMiss;
	}

	public void setCriticalMiss(boolean criticalMiss) 
	{
		this.criticalMiss = criticalMiss;
	}

	public boolean isCriticalHit() 
	{
		return criticalHit;
	}

	public void setCriticalHit(boolean criticalHit) 
	{
		this.criticalHit = criticalHit;
	}

	
	public RollAction getAction() 
	{
		return action;
	}
	
	public HeroAction rebuild()
	{
		return HeroAction.create(this.name, this.key, this.text, null);
	}
}
