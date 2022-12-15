package com.dnd.botTable.actions;

public class FinalAction extends FactoryAction
{
	private static final long serialVersionUID = 1L;

	public static FinalAction create(FactoryAction action)
	{
		FinalAction target = new FinalAction();
		target.key = action.getKey();
		target.text = action.getText();
		target.mainAct = true;
		target.mediator = false;
		target.nextStep = action.nextStep;
		target.setLocalData(action.getLocalData());
		return target;
	}
	public String toString()
	{
		String answer = name + "| FINAL ACTION";

	
		return answer;
	}
	
	@Override
	public FinalAction continueAction(String name)
	{
		return this;
	}
	
}
