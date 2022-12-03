package com.dnd.dndTable.rolls.actions;

import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.dndTable.rolls.Dice;

public class RollAction extends SimpleRollAction
{
	private static final long serialVersionUID = 1L;
	private Stat depends;
	private boolean proficiency;
	private List<Dice> buff;

	
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

	public List<Dice> getBuff() {
		return buff;
	}

	public void setBuff(List<Dice> buff) {
		this.buff = buff;
	}
	
}
