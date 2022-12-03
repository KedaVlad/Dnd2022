package com.dnd.dndTable.rolls.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class HeroAction implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private String text;

	private List<HeroAction> nextStep = new ArrayList<>();

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
	
}
