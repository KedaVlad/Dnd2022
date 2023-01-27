package com.dnd.botTable.actions;


import com.dnd.dndTable.creatingDndObject.characteristic.Stat.Stats;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession.Proficiency;
import com.dnd.dndTable.rolls.Dice;

public class RollActionBuilder extends BaseActionBuilder<RollActionBuilder> 
{

	private Stats depends;
	private Proficiency proficiency;
	private Dice[] base;
	
	
	RollActionBuilder(){}
	
    public RollActionBuilder statDepend(Stats stat)
    {
    	this.depends = stat;
    	return this;
    }
    
    public RollActionBuilder proficiency(Proficiency proficiency)
    {
    	this.proficiency = proficiency;
    	return this;
    }
    
    public RollActionBuilder diceCombo(Dice...dices)
    {
    	this.base = dices;
    	return this;
    }
    
    public RollAction build()
    {
    	RollAction answer = new RollAction();
    	answer.name = this.name;
    	answer.key = this.key;
    	answer.location = this.location;
    	answer.depends = this.depends;
    	answer.proficiency = this.proficiency;
    	answer.setBase(this.base);
    	return answer;
    }

	@Override
	protected RollActionBuilder self() {
		// TODO Auto-generated method stub
		return this;
	}
}
