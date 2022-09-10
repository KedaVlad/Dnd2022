package com.dnd.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.Source;
import com.dnd.creatingCharacter.skills.Possession;
import com.dnd.creatingCharacter.skills.Skill;
import com.dnd.creatingCharacter.skills.Spell;
import com.dnd.creatingCharacter.skills.Trait;
import com.dnd.creatingCharacter.skills.Workmanship;


public class WorkmanshipFactory implements Factory, Source {


	private  final static File[] workmanship = {new File(skillsSource), new File(spellsSource), new File(possessionsSource), new File(traitsSource)};

	public static void getWorkmanship(List<String> workmanship)
	{
		for(int i = 0; i < workmanship.size(); i++)
		{
			String nameCheker;
			if(workmanship.get(i).equals("^*[a-zA-Z]"))
			{
				nameCheker = workmanship.get(i).replaceAll("^*([a-zA-Z])", "$1");
				if(!CharacterFactory.getActualGameCharacter().getMySkills().containsKey(nameCheker))
				{
					CharacterFactory.getActualGameCharacter().getMySkills().put(nameCheker,(Skill)WorkmanshipFactory.createWorkmanship(workmanship.get(i)));
				}
			}
			else if(workmanship.get(i).equals("^>[a-zA-Z]"))
			{
				nameCheker = workmanship.get(i).replaceAll("^>([a-zA-Z])", "$1");
				if(!CharacterFactory.getActualGameCharacter().getMySpells().containsKey(nameCheker))
				{
					CharacterFactory.getActualGameCharacter().getMySpells().put(nameCheker,(Spell)WorkmanshipFactory.createWorkmanship(workmanship.get(i)));
				}
			}
			else if(workmanship.get(i).equals("^<[a-zA-Z]"))
			{
				nameCheker = workmanship.get(i).replaceAll("^<([a-zA-Z])", "$1");
				if(!CharacterFactory.getActualGameCharacter().getMyPossession().containsKey(nameCheker))
				{
					CharacterFactory.getActualGameCharacter().getMyPossession().put(nameCheker,(Possession)WorkmanshipFactory.createWorkmanship(workmanship.get(i)));
				}
				
			}
			else if(workmanship.get(i).equals("^-[a-zA-Z]"))
			{
				nameCheker = workmanship.get(i).replaceAll("^-([a-zA-Z])", "$1");
				if(!CharacterFactory.getActualGameCharacter().getMySkills().containsKey(nameCheker))
				{
					CharacterFactory.getActualGameCharacter().getMySkills().put(nameCheker,(Trait)WorkmanshipFactory.createWorkmanship(workmanship.get(i)));
				}
				
			}
			
		}
	}
	
	public void addSomeWorkmanship(String... nameWorkmanship) 
	{

		List<String> pool = Arrays.asList(nameWorkmanship);
		getWorkmanship(pool);
	}
	
	private static Workmanship createWorkmanship(String name) 
	{

		switch(name)
		{
		case "^*[a-zA-Z]":	
			return createSkill(new Skill(name));
		case "^>[a-zA-Z]":	
			return createSpell(new Spell(name));
		case "^<[a-zA-Z]":	
			return createPossession(new Possession(name));
		case "^-[a-zA-Z]":	
			return createTrait(new Trait(name));
		}

		return null;	

	}

	private static Trait createTrait(Trait trait) 
	{
		return trait;
	}

	private static Possession createPossession(Possession possession) 
	{

		return possession;
	}

	private static Spell createSpell(Spell spell) 
	{

		return spell;
	}

	private static Skill createSkill(Skill skill) 
	{

		return skill;
	}

}