package com.dnd.botTable.actions.factoryAction;

public class FinalAction extends FactoryAction
{
	private static final long serialVersionUID = 1L;

	public static FinalAction create(FactoryAction action)
	{
		FinalAction target = new FinalAction();
		target.setName("FINAL");
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
		String localData = "local data - ";
		for(String string: this.getLocalData())
		{
			localData += string + " ";
		}
		return "  | FACTORY ACTION :" + name + localData + "|  ";
	}
	
	@Override
	public FinalAction continueAction(String name)
	{
		return this;
	}
	
}
