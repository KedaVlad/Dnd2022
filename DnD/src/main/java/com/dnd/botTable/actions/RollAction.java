package com.dnd.botTable.actions;

import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.botTable.Action;
import com.dnd.dndTable.rolls.AttackModification;

public class RollAction extends SimpleRollAction
{
	
	public static RollAction create(long key, AttackModification attack, List<Action> nextStep, Stat depends, boolean proficiency)
	{
		
		RollAction answer = new RollAction();
		answer.key = key;
		answer.name = attack.getName();
		answer.mainAct = true;
		answer.mediator = false;
		answer.setNextStep(nextStep);
		answer.depends = depends;
		answer.proficiency = proficiency;
		answer.setBase(attack.getDamage()); 
		return  answer;
	}

	private static final long serialVersionUID = 1L;
	private Stat depends;
	private boolean proficiency;

	
	public Stat getDepends() {
		return depends;
	}

	public void setDepends(Stat depends) {
		this.depends = depends;
	}

	public boolean isProficiency() {
		return proficiency;
	}

	public void setProficiency(boolean proficiency) {
		this.proficiency = proficiency;
	}

	
}
