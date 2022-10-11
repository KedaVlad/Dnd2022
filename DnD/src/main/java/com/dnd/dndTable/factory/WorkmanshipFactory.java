package com.dnd.dndTable.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Source;
import com.dnd.Log.Place;
import com.dnd.dndTable.creatingDndObject.*;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;
import com.dnd.dndTable.creatingDndObject.skills.Features;
import com.dnd.dndTable.creatingDndObject.skills.Possession;
import com.dnd.dndTable.creatingDndObject.skills.Spell;
import com.dnd.dndTable.creatingDndObject.skills.Trait;


public class WorkmanshipFactory implements Factory, Source, KeyWallet {


	private final static File[] workmanship = {new File(skillsSource), new File(spellsSource), new File(possessionsSource), new File(traitsSource)};

	public static CharacterDnd getWorkmanship(CharacterDnd character, List<String> workmanship)
	{ 
		Log.add("getWorkmanship", Place.DND, Place.FACTORY, Place.WORKMANSHIP);

		String cleanName;

		for(String name: workmanship)
		{
			
			cleanName = name.replaceAll(workmanshipKey, "$2");
			
			if(name.contains(skillKey))
			{
				
				if(!character.getGameEngine().getMyWorkmanship().getMyFeatures().contains(new Features(cleanName)))
				{
					character.getGameEngine().getMyWorkmanship().getMyFeatures().add((Features)WorkmanshipFactory.createFeatures(cleanName));

				}

			}
			else if(name.contains(spellKey))
			{
				if(!character.getGameEngine().getMyWorkmanship().getMySpells().contains(new Spell(cleanName)))
				{
					character.getGameEngine().getMyWorkmanship().getMySpells().add((Spell)WorkmanshipFactory.createSpell(cleanName));
				}
			}
			else if(name.contains(possessionKey))
			{
				if(!character.getGameEngine().getMyWorkmanship().getMyPossession().contains(new Possession(cleanName)))
				{
					character.getGameEngine().getMyWorkmanship().getMyPossession().add((Possession)WorkmanshipFactory.createPossession(name));
				}
			}
			else if(name.contains(traitKey))
			{
				if(!character.getGameEngine().getMyWorkmanship().getMyFeatures().contains(new Trait(cleanName)))
				{
					character.getGameEngine().getMyWorkmanship().getMyFeatures().add((Trait)WorkmanshipFactory.createTrait(cleanName));
				}
			}
		}
		return character;
	}

	public static List<String> getWorkmanshipClass(ClassDnd classDnd)
	{
		List<String> pool = new ArrayList<>();
		Scanner classScanner = null;
		Pattern findNameSkills = Pattern.compile(skillKey);
		Pattern findNameSpells = Pattern.compile(spellKey);
		Pattern findNamePossession = Pattern.compile(possessionKey);
		try {
			classScanner = new Scanner(classDnd.getMyClassMainFile());
			boolean checkLvl = false;
			while(classScanner.hasNextLine()&& !checkLvl) {
				String workmanship = classScanner.nextLine();
				Matcher rightSkill = findNameSkills.matcher(workmanship);
				Matcher rightSpell = findNameSpells.matcher(workmanship);
				Matcher rightPossession = findNamePossession.matcher(workmanship);
				if(rightSkill.find()||rightSpell.find()||rightPossession.find()) 
				{
					pool.add(workmanship);

				} else if(workmanship.contains(classDnd.getLvl()+ 1 + "")) {
					checkLvl = true;
				}
			} 

		}
		catch (FileNotFoundException e) 
		{

			e.printStackTrace();
		}
		finally 
		{
			classScanner.close();
		}
		
		return pool;
	}

	public static List<String> getWorkmanshipRace(RaceDnd raceDnd)
	{
		List<String> pool = new ArrayList<>();
		Scanner classScanner = null;
		Pattern findNameSkills = Pattern.compile(skillKey);
		Pattern findNameSpells = Pattern.compile(spellKey);
		Pattern findNamePossession = Pattern.compile(possessionKey);
		try {
			classScanner = new Scanner(new File(raceSource + raceDnd.getRaceName() + "\\" + raceDnd.getSubRace() + ".txt"));
			while(classScanner.hasNextLine()) {
				String workmanship = classScanner.nextLine();
				Matcher rightSkill = findNameSkills.matcher(workmanship);
				Matcher rightSpell = findNameSpells.matcher(workmanship);
				Matcher rightPossession = findNamePossession.matcher(workmanship);
				if(rightSkill.find()||rightSpell.find()||rightPossession.find()) 
				{
					pool.add(workmanship);
				} 
			} 
		}
		catch (FileNotFoundException e) 
		{

			e.printStackTrace();
		}
		finally 
		{
			classScanner.close();
		}
		return pool;
	}

	public void addSomeWorkmanship(CharacterDnd character,String... nameWorkmanship) 
	{

		List<String> pool = Arrays.asList(nameWorkmanship);
		getWorkmanship(character, pool);
	}

	private static Trait createTrait(String trait) 
	{
		return new Trait(trait);
	}

	private static Possession createPossession(String possession) 
	{

		return new Possession(possession);
	}

	private static Spell createSpell(String spell) 
	{

		return new Spell(spell);
	}

	private static Features createFeatures(String skill) 
	{
		return new Features(skill);
	}

	public static File[] getWorkmanship() 
	{
		return workmanship;
	}

}