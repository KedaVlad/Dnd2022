package com.dnd.botTable.actions.factoryAction;

import com.dnd.dndTable.ObjectDnd;

public class FinalTargetAction extends FinalAction
{
	private static final long serialVersionUID = 1L;
	private ObjectDnd target;
	public static FinalTargetAction create(FactoryAction action, ObjectDnd targetObject)
	{
		FinalTargetAction target = new FinalTargetAction();
		target.target =targetObject;
		target.setName("FINAL");
		target.key = action.getKey();
		target.text = action.getText();
		target.mainAct = true;
		target.setMediator(false);
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
	public ObjectDnd getTarget() {
		return target;
	}
	
}
