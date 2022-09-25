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
import com.dnd.Source;
import com.dnd.dndTable.*;
import com.dnd.dndTable.creatingDndObject.*;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;
import com.dnd.dndTable.creatingDndObject.skills.Possession;
import com.dnd.dndTable.creatingDndObject.skills.Skill;
import com.dnd.dndTable.creatingDndObject.skills.Spell;
import com.dnd.dndTable.creatingDndObject.skills.Trait;
import com.dnd.dndTable.creatingDndObject.skills.Workmanship;


public class WorkmanshipFactory implements Factory, Source, KeyWallet {


	private final static File[] workmanship = {new File(skillsSource), new File(spellsSource), new File(possessionsSource), new File(traitsSource)};

	public static void getWorkmanship(CharacterDnd character, List<String> workmanship)
	{
		for(int i = 0; i < workmanship.size(); i++)
		{
			String nameCheker;
			if(workmanship.get(i).equals(skillKey))
			{
				nameCheker = workmanship.get(i).replaceAll(workmanshipKey, "$1");
				if(!character.getMySkills().containsKey(nameCheker))
				{
					character.getMySkills().put(nameCheker,(Skill)WorkmanshipFactory.createWorkmanship(workmanship.get(i)));
				}
			}
			else if(workmanship.get(i).equals(spellKey))
			{
				nameCheker = workmanship.get(i).replaceAll(workmanshipKey, "$1");
				if(!character.getMySpells().containsKey(nameCheker))
				{
					character.getMySpells().put(nameCheker,(Spell)WorkmanshipFactory.createWorkmanship(workmanship.get(i)));
				}
			}
			else if(workmanship.get(i).equals(possessionKey))
			{
				nameCheker = workmanship.get(i).replaceAll(workmanshipKey, "$1");
				if(!character.getMyPossession().containsKey(nameCheker))
				{
					character.getMyPossession().put(nameCheker,(Possession)WorkmanshipFactory.createWorkmanship(workmanship.get(i)));
				}

			}
			else if(workmanship.get(i).equals(traitKey))
			{
				nameCheker = workmanship.get(i).replaceAll(workmanshipKey, "$1");
				if(!character.getMySkills().containsKey(nameCheker))
				{
					character.getMySkills().put(nameCheker,(Trait)WorkmanshipFactory.createWorkmanship(workmanship.get(i)));
				}

			}

		}
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
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {
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
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {
			classScanner.close();
		}
		return pool;
	}

	public void addSomeWorkmanship(CharacterDnd character,String... nameWorkmanship) 
	{

		List<String> pool = Arrays.asList(nameWorkmanship);
		getWorkmanship(character, pool);
	}

	private static Workmanship createWorkmanship(String name) 
	{

		switch(name)
		{
		case "^\\*[a-zA-Z]*":	
			return createSkill(new Skill(name));
		case "^>[a-zA-Z]*":	
			return createSpell(new Spell(name));
		case "^<[a-zA-Z]*":	
			return createPossession(new Possession(name));
		case "^-[a-zA-Z]*":	
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

	public static File[] getWorkmanship() {
		return workmanship;
	}

}