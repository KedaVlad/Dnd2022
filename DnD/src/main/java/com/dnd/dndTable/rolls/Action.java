package com.dnd.dndTable.rolls;

import java.util.List;

public class Action {
	
	private String name;
	private boolean trueStricke;
	private boolean oneStep;
	private Article stepOne;
	private Article stepTwo;
	private List<String> spesials;
	
	Action(String name, Article attack, Article hit, boolean trueStrike, List<String> spesials)
	{
		this.setName(name);
		this.trueStricke = trueStrike;
		this.oneStep = false;
		this.setStepOne(attack);
		this.setStepTwo(hit);
		this.setSpesials(spesials);
	}
	
	Action(String name, boolean trueStricke, Article attack, Article hit)
	{
		this.setName(name);
		this.trueStricke = trueStricke;
		this.oneStep = false;
		this.setStepOne(attack);
		this.setStepTwo(hit);
	}
	
	Action(String name, boolean oneStep, Article attack, List<String> spesials)
	{
		this.setName(name);
		this.trueStricke = false;
		this.oneStep = oneStep;
		this.setStepOne(attack);
		this.setSpesials(spesials);
	}
	
	Action(String name, boolean oneStep, Article attack)
	{
		this.setName(name);
		this.trueStricke = false;
		this.oneStep = oneStep;
		this.setStepOne(attack);
	}
	
	Action(String name, Article attack)
	{
		this.setName(name);
		this.trueStricke = false;
		this.oneStep = false;
		this.setStepOne(attack);
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public boolean isTrueStricke() 
	{
		return trueStricke;
	}

	public void setTrueStricke(boolean truStricke) 
	{
		this.trueStricke = truStricke;
	}

	public Article getStepOne() 
	{
		return stepOne;
	}

	public void setStepOne(Article attack) 
	{
		this.stepOne = attack;
	}

	public Article getStepTwo() 
	{
		return stepTwo;
	}

	public void setStepTwo(Article hit) 
	{
		this.stepTwo = hit;
	}

	public List<String> getSpesials() 
	{
		return spesials;
	}

	public void setSpesials(List<String> spesials)
	{
		this.spesials = spesials;
	}

	public boolean isOneStep() {
		return oneStep;
	}

	public void setOneStep(boolean oneStep) {
		this.oneStep = oneStep;
	}
	
}
