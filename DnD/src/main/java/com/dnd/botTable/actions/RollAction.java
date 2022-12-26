package com.dnd.botTable.actions;

import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.botTable.Action;
import com.dnd.dndTable.creatingDndObject.Rolls.Proficiency;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
import com.dnd.dndTable.rolls.Dice;

public class RollAction extends SimpleRollAction
{
	
	public static RollAction create(String name, List<Dice> base, long key, Action[][] nextStep, Stat depends, Proficiency proficiency)
	{
		
		RollAction answer = new RollAction();
		answer.key = key;
		answer.name = name;
		answer.mainAct = true;
		answer.mediator = false;
		answer.setNextStep(nextStep);
		answer.setDepends(depends);
		answer.setProficiency(proficiency);
		answer.setBase(base); 
		return  answer;
	}

	private static final long serialVersionUID = 1L;
	private Stat depends;
	private Proficiency proficiency;
	
	public Stat getDepends() 
	{
		return depends;
	}

	public boolean isProficiency() 
	{
		return proficiency != null;
	}
	
	public Proficiency getProficiency() 
	{
		return proficiency;
	}

	public void setDepends(Stat depends)
{
		this.depends = depends;
	}

	public void setProficiency(Proficiency proficiency) 
	{
		this.proficiency = proficiency;
	}

	
	
}
