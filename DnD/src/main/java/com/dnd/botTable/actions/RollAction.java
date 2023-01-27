package com.dnd.botTable.actions;

import com.dnd.dndTable.creatingDndObject.characteristic.Stat.Stats;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession.Proficiency;
import com.dnd.dndTable.rolls.Dice;

public class RollAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	Stats depends;
	Proficiency proficiency;
	Dice[] base;

	RollAction(){}

	public static RollActionBuilder buider()
	{
		return new RollActionBuilder();
	}

	public void addDicesToStart(Dice...dices)
	{
		if(base == null)
		{
			base = dices;
		}
		else
		{
			Dice[] result = new Dice[base.length + dices.length];
			for(int i = 0; i < dices.length; i++)
			{
				result[i] = dices[i];
			}
			int treker = 0;
			for(int i = dices.length; i < result.length; i++)
			{
				result[i] = base[treker];
				treker++;
			}
			base = result;
		}
	}

	public void addDicesToEnd(Dice...dices)
	{
		if(base == null)
		{
			base = dices;
		}
		else
		{
			Dice[] result = new Dice[base.length + dices.length];
			for(int i = 0; i < base.length; i++)
			{
				result[i] = base[i];
			}
			int treker = 0;
			for(int i = base.length; i < result.length; i++)
			{
				result[i] = dices[treker];
				treker++;
			}
			base = result;
		}
	}

	public Stats getDepends() 
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

	public void setDepends(Stats depends)
	{
		this.depends = depends;
	}

	public void setProficiency(Proficiency proficiency) 
	{
		this.proficiency = proficiency;
	}

	@Override
	public String[][] buildButtons() 
	{
		return null;
	}

	@Override
	public BaseAction continueAction(String key) 
	{
		return this;
	}

	@Override
	public boolean hasButtons() 
	{
		return false;
	}

	public Dice[] getBase() {
		return base;
	}

	public void setBase(Dice[] base) {
		this.base = base;
	}

	@Override
	public boolean replyContain(String string) 
	{
		return false;
	}
}

