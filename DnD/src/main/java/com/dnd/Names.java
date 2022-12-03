package com.dnd;

public interface Names 
{

	public enum Refresh
	{
		FULL, HALF
	}
	
	public enum Stat
	{
		STRENGTH("Strength"), 
		DEXTERITY("Dexterity"), 
		CONSTITUTION("Constitution"), 
		INTELLIGENSE("Intelligense"), 
		WISDOM("Wisdom"), 
		CHARISMA("Charisma");
		
		String name;
		Stat(String name)
		{
			this.name = name;
		}
		
		public String toString()
		{
			return name;
		}
		
	}
	
	public enum SaveRoll
	{
		SR_STRENGTH("SR Strength"), 
		SR_DEXTERITY("SR Dexterity"), 
		SR_CONSTITUTION("SR Constitution"), 
		SR_INTELLIGENSE("SR Intelligense"), 
		SR_WISDOM("SR Wisdom"), 
		SR_CHARISMA("SR Charisma");
		
		String name;
		
		SaveRoll(String name)
		{
			this.name = name;
		}
		
		public String toString()
		{
			return name;
		}
		
	}
	
	public enum Skill
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
		
		Skill(String name)
		{
			this.name = name;
		}
		
		public String toString()
		{
			return name;
		}
		
	}
	
	public enum TypeDamage
	{
		FIZ, MAG
	}
}
