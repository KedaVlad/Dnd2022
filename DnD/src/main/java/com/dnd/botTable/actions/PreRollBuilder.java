package com.dnd.botTable.actions;

import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class PreRollBuilder extends BaseActionBuilder<PreRollBuilder>  {

	RollAction roll;
	
	PreRollBuilder(){}
	
	public PreRollBuilder roll(RollAction roll)
	{
		this.roll = roll;
		this.roll.addDicesToStart(new Dice("D20", 0, Roll.D20));
		return this;
	}
	
	public PreRoll build()
	{
		PreRoll action = new PreRoll();
		action.replyButtons = this.replyButtons;
		action.action = this.roll;
		action.key = this.key;
		action.location = this.location;
		action.name = this.name;
		return action;
	}
	
	@Override
	protected PreRollBuilder self() 
	{
		return this;
	}

}
