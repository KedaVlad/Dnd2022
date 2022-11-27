package com.dnd.dndTable.creatingDndObject.workmanship.magicEffects;

import com.dnd.dndTable.rolls.Action;


public class Damage extends Effect {

	private static final long serialVersionUID = 1L;
	private Action action;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}

}
