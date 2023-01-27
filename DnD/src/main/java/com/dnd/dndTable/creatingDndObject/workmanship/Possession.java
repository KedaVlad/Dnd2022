package com.dnd.dndTable.creatingDndObject.workmanship;

import java.io.Serializable;

import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor.Armors;
import com.dnd.dndTable.creatingDndObject.bagDnd.Tool.Tools;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.Weapons;
import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("POSSESSION")
public class Possession implements Serializable, ObjectDnd
{
	public enum Proficiency
	{
		BASE, HALF, COMPETENSE
	}

	private static final long serialVersionUID = 863271851968078819L;

	private Proficiency prof;
	private String name;
	public Possession() {}

	public Possession(String name) 
	{
		this.name = name;
		this.prof = Proficiency.BASE;
	}

	public Possession(String name, Proficiency prof)
	{
		this.name = name;
		this.prof = prof;
	}

	public String toString()
	{
		if(prof.equals(Proficiency.HALF))
		{
			return name + " (half proficiency)";
		}
		else if(prof.equals(Proficiency.COMPETENSE))
		{
			return name + " (competense)";
		}
		else
		{
			return name;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Proficiency getProf() {
		return prof;
	}

	public void setProf(Proficiency prof) {
		this.prof = prof;
	}

	public static String hintList()
	{
		String answer = "";
		answer += "Weapon: ";
		for(int i = 0; i < Weapons.values().length; i++)
		{
			if(i == Weapons.values().length - 1)
			{
				answer += Weapons.values()[i].toString() +".";
			}
			else
			{
				answer += Weapons.values()[i].toString() +", ";
			}
		}
		answer += "\nTool: ";
		for(int i = 0; i < Tools.values().length; i++)
		{
			if(i == Tools.values().length - 1)
			{
				answer += Tools.values()[i].toString() +".";
			}
			else
			{
				answer += Tools.values()[i].toString() +", ";
			}
		}
		answer += "\nArmor: ";
		for(int i = 0; i < Armors.values().length; i++)
		{
			if(i == Armors.values().length - 1)
			{
				answer += Armors.values()[i].toString() +".";
			}
			else
			{
				answer += Armors.values()[i].toString() +", ";
			}
		}
		return answer;
	}
}
