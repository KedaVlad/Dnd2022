package com.dnd.dndTable.rolls.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class HeroAction extends Action
{
	private static final long serialVersionUID = 1L;
	private String name;
	private String text;

	private List<HeroAction> nextStep = new ArrayList<>();

	
	public HeroAction(String name, String text, List<HeroAction> nextStep)
	{
		this.name = name;
		this.text = text;
		this.nextStep = nextStep;
	}
	public HeroAction() {}

	public String getText() 
	{
		return text;
	}

	public void setText(String text) 
	{
		this.text = text;
	}
	
	public List<HeroAction> getNextStep() 
	{
		return nextStep;
	}

	public void setNextStep(List<HeroAction> nextStep) 
	{
		this.nextStep = nextStep;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isLastStep()
	{
		return nextStep == null || nextStep.isEmpty();
	}
	
}
