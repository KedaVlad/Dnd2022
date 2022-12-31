package com.dnd.botTable.actions.dndAction;

import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;
import com.dnd.botTable.Action;

public class ComplexChenge extends ChangeAction
{
	private static final long serialVersionUID = 1L;
	private int step = 1;
	private String[][] nextStep;
	private List<String> poolAnswer;

	
	
	public static ComplexChenge create(ChangeAction action, String text, String[][] nextStep) 
	{
		ComplexChenge answer = new ComplexChenge();
		answer.target = action.target;
		answer.key = action.getKey();
		answer.answer = action.answer;
		answer.poolAnswer = new ArrayList<>();
		answer.getPoolAnswer().add(action.getAnswer());
		answer.mainAct = true;
		answer.setName(action.getName() + answer.step);
		answer.text = text;
		answer.nextStep = nextStep;
		return answer;
	}

	public ComplexChenge addQuestion(String text, String[][] buttons)
	{
		this.text = text;
		this.nextStep = buttons;
		return this;
	}
	
	@Override
	public Action continueAction(String name) 
	{
		ComplexChenge answer = new ComplexChenge();
		answer.key = this.key;
		answer.answer = this.answer;
		answer.mainAct = true;
		answer.target = this.target;
		answer.poolAnswer = new ArrayList<>();
		answer.poolAnswer.addAll(this.getPoolAnswer());
		answer.getPoolAnswer().add(name);
		answer.step = this.step;
		answer.step++;
		answer.setName(this.getName()+answer.step);
		return answer;
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

	public List<String> getPoolAnswer() {
		return poolAnswer;
	}
	
}
