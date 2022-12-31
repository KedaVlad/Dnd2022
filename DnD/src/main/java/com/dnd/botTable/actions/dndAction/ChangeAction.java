package com.dnd.botTable.actions.dndAction;

import com.dnd.Log;
import com.dnd.botTable.Action;

public class ChangeAction extends RegistrateAction
{
	private static final long serialVersionUID = 1L;
	protected String[][] nextStep;
	protected String answer;

	
	
	public static ChangeAction create(RegistrateAction action, String text, String[][] nextStep) 
	{
		ChangeAction answer = new ChangeAction();
		answer.key = action.getKey();
		answer.mainAct = true;
		answer.setName(action.getName() + "Change");
		answer.text = text;
		answer.target = action.target;
		answer.nextStep = nextStep;
		return answer;
	}

	public String getAnswer() {
		return answer;
	}
	
	@Override
	public Action continueAction(String name) {
		
		
				answer = name;
		
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
		return nextStep != null && nextStep.length != 0;
	}
}
