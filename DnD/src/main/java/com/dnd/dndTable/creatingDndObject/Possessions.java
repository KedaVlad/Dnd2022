package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession.Proficiency;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class Possessions implements Serializable 
{

	private Dice proficiency;
	
	private static final long serialVersionUID = 1L;
	private List<Possession> possessions;
	
	void setProfisiency(int value)
	{
		this.proficiency.setBuff(value);
	}
	
	public List<Possession> getPossessions() 
	{
		return possessions;
	}

	public Possessions()
	{
		possessions = new ArrayList<>();
		proficiency = new Dice("Proficiency", 2, Roll.NO_ROLL);
	}
	
	public Dice getDice(Proficiency prof)
	{
		Dice answer = new Dice("Proficiency", 0, Roll.NO_ROLL);
		switch(prof)
		{
		case HALF:
			answer.setBuff(proficiency.getBuff()/2);
			break;
		case BASE:
			answer.setBuff(proficiency.getBuff());
			break;
		case COMPETENSE:
			answer.setBuff(proficiency.getBuff()*2);
			break;
		}
		return answer;
	}
	
	public Proficiency getProf(String name)
	{
		for(Possession target: getPossessions())
		{
			if(target.getName().contains(name))
			{
				return target.getProf();
			}
		}
		return null;
	}

	public void add(Possession possession)
	{
		for(Possession target: getPossessions())
		{
			if(target.getName().contains(possession.getName()))
			{
				target.setProf(upOrStay(target.getProf(), possession.getProf()));
				return;
			}
		}
		getPossessions().add(possession);
	}
	
	private Proficiency upOrStay(Proficiency first, Proficiency second)
	{
		if(second.equals(Proficiency.COMPETENSE))
		{
			return Proficiency.COMPETENSE;
		}
		else if(first.equals(Proficiency.BASE))
		{
			return Proficiency.BASE;
		}
		else
		{
			return second;
		}
	}
	
	public void add(String name)
	{
		for(Possession target: getPossessions())
		{
			if(target.getName().equalsIgnoreCase(name))
			{
				return;
			}
		}
		getPossessions().add(new Possession(name));
	}
	
	public boolean holded(String name)
	{
		for(Possession target: getPossessions())
		{
			if(target.getName().contains(name))
			{
				return true;
			}
		}
		return false;
	}
	
}
