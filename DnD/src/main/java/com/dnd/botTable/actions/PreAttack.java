package com.dnd.botTable.actions;

import com.dnd.botTable.Action;

public class PreAttack extends HeroAction
{
	private static final long serialVersionUID = 1L;

	private int status;
	private String[][] nextStep;
	private AttackAction action;
	private boolean criticalMiss;
	private boolean criticalHit;

	
	public static PreAttack create(AttackAction action)
	{
		PreAttack answer = new PreAttack();
		answer.action = action;
		answer.name = action.getName();
		answer.key = action.getKey();
		answer.mainAct = true;
		answer.mediator = false;
		answer.text = action.getText();
		answer.nextStep = new String[][] {{"ADVENTURE", "BASIC", "DISADVENTURE"}};
		return answer;
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
		else
		{
			this.status = 2;
		}
		
		return this;
	}

	@Override
	public String[][] buildButtons()
	{
	
		return nextStep;
	}

	public AttackAction getAction() 
	{
		return action;
	}

	@Override
	protected boolean hasButtons() 
	{
		return (nextStep != null) && (nextStep.length != 0);
	}

	
	public int getStatus() 
	{
		return status;
	}


	public boolean isCriticalMiss() {
		return criticalMiss;
	}


	public void setCriticalMiss(boolean criticalMiss) {
		this.criticalMiss = criticalMiss;
	}


	public boolean isCriticalHit() {
		return criticalHit;
	}


	public void setCriticalHit(boolean criticalHit) {
		this.criticalHit = criticalHit;
	}

}
