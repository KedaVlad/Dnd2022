package com.dnd.botTable.actions;

import com.dnd.botTable.Action;

public class BotAction extends Action{

	private static final long serialVersionUID = 1L;

	private String[][] nextStep;
	private String answer;
	

	public static BotAction create(String name, long key, boolean mainAct, boolean mediator, String text, String[][] nextStep) {
		
		BotAction target = new BotAction();
		target.setName(name);
		target.key = key;
		target.mainAct = mainAct;
		target.mediator = mediator;
		target.text = text;
		target.nextStep = nextStep;
		return target;
	}

	public String[][] getNextStep() {
		return nextStep;
	}

	public void setNextStep(String[][] nextStep) {
		this.nextStep = nextStep;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public Action continueAction(String key) 
	{
		BotAction answer = new BotAction();
		answer.setName(this.name);
		answer.key = this.key;
		answer.mainAct = this.mainAct;
		answer.mediator = this.mediator;
		answer.text = this.text;
		answer.answer = key;
		return answer;
	}

	
	@Override
	protected String[][] buildButtons() 
	{
		return nextStep;
	}

	
	@Override
	public boolean hasButtons() {
		
		return (nextStep != null) && (nextStep.length != 0);
	}
	
}
