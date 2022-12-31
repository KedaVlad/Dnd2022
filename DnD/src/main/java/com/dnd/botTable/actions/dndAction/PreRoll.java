package com.dnd.botTable.actions.dndAction;

import com.dnd.botTable.Action;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class PreRoll extends HeroAction
{
	private static final long serialVersionUID = 1L;

	protected String status;
	protected String[][] nextStep = new String[][] {{"ADVENTURE", "BASIC", "DISADVENTURE"}};
	private RollAction action;
	private boolean criticalMiss;
	private boolean criticalHit;

	public static PreRoll create(String text,RollAction action)
	{
		PreRoll answer = new PreRoll();
		answer.action = action;
		answer.getAction().getBase().add(0, new Dice("D20", 0, Roll.D20));
		answer.setName(action.getName());
		answer.key = action.getKey();
		answer.mainAct = true;
		answer.text = text;
		return answer;
	}
	
	public String getStatus() 
	{
		return status;
	}
	@Override
	public Action continueAction(String answer) 
	{
		this.status = answer;
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
	
}
