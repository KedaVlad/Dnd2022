package com.dnd.dndTable.creatingDndObject.characteristic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.characteristic.Stat.Stats;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession.Proficiency;

public class Skill implements Serializable, ObjectDnd
{
	private static final long serialVersionUID = 1L;
	private String name;
	private Stats depends;
	private Proficiency proficiency;
	private List <String> spesial;

	Skill(String name, Stats depends)
	{
		this.setName(name);
		this.setDepends(depends);
		spesial = new ArrayList<>();
	}
	
	public String skillInfo()
	{
		String answer = name + "depends on " + depends.toString();
		if(proficiency != null)
		{
			if(proficiency.equals(Proficiency.COMPETENSE))
			{
				return answer += " + competense";
			}
			else if(proficiency.equals(Proficiency.BASE))
			{
				return answer += " + proficiency";
			}
			else
			{
				return answer += " + half proficiency";
			}
		}
		else
		{
			return answer;
		}
	}
	
	public String getSpesial()
	{
		String answer = "";
		for(String string: spesial)
		{
			answer += string + "\n";
		}
		return answer;
	}
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Stats getDepends() 
	{
		return depends;
	}

	public void setDepends(Stats depends) 
	{
		this.depends = depends;
	}

	public boolean isProficiency() 
	{
		return proficiency != null;
	}

	public void addSpesial(String spesial) 
	{
		this.spesial.add(spesial);
	}

	
	
	public Proficiency getProficiency() 
	{
		return proficiency;
	}

	public void setProficiency(Proficiency proficiency) 
	{
		this.proficiency = proficiency;
	}
}

class SaveRoll extends Skill
{
	SaveRoll(String name, Stats depends) 
	{
		super(name, depends);
	}

	private static final long serialVersionUID = 1L;
	
}

enum SaveRolls
{
	SR_STRENGTH("SR Strength"), 
	SR_DEXTERITY("SR Dexterity"), 
	SR_CONSTITUTION("SR Constitution"), 
	SR_INTELLIGENSE("SR Intelligense"), 
	SR_WISDOM("SR Wisdom"), 
	SR_CHARISMA("SR Charisma");
	
	String name;
	
	SaveRolls(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return name;
	}
	
}



enum Skills
{
	ACROBATICS("Acrobatics"), 
	ANIMAL_HANDING("Animal Handing"),
	ARCANA("Arcana"),
	ATHLETIX("Athletix"),
	DECEPTION("Deception"),
	HISTORY("History"),
	INSIGHT("Insight"),
	INTIMIDATION("Intimidation"),
	INVESTIGATION("Investigation"),
	MEDICINE("Medicine"),
	NATURE("Nature"),
	PERCEPTION("Perception"),
	PERFORMANCE("Performance"),
	PERSUASION("Persuasion"),
	RELIGION("Religion"),
	SLEIGHT_OF_HAND("Sleigth of Hand"),
	STELTH("Stelth"),
	SURVIVAL("Survival");
	
	String name;
	
	Skills(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return name;
	}
	
}
