package com.dnd.botTable.actions;


public class PreRoll extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	String[][] nextStep;
	RollAction action;
	private String status;
	private boolean criticalMiss;
	private boolean criticalHit;
	 PreRoll()
	 {
		 nextStep = new String[][] {{"ADVENTURE", "BASIC", "DISADVENTURE"}};
	 }
	
	public static PreRollBuilder builder()
	{
		return new PreRollBuilder();
	}
	

	

	@Override
	public PreRoll continueAction(String answer) 
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
	public boolean hasButtons() 
	{
		return nextStep != null && nextStep.length != 0;
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

	public String getStatus() {
		return status;
	}

	@Override
	public boolean replyContain(String string) 
	{
		return string.equals("ADVENTURE") 
				||string.equals("BASIC") 
				||string.equals("DISADVENTURE");
	}
	
}
