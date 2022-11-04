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
import com.dnd.dndTable.creatingDndObject.*;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;
import com.dnd.dndTable.creatingDndObject.skills.Feature;
import com.dnd.dndTable.creatingDndObject.skills.Possession;
import com.dnd.dndTable.creatingDndObject.skills.Spell;
import com.dnd.dndTable.creatingDndObject.skills.Trait;


abstract class WorkmanshipFactory implements Source, KeyWallet {


	private final static File[] workmanship = {new File(skillsSource), new File(spellsSource), new File(possessionsSource), new File(traitsSource)};


	private static Object reader(File file, String name) //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	{
		Scanner scanner = null;
		Pattern findObject = Pattern.compile(name);
		Pattern nextObject = Pattern.compile(name.replaceAll(workmanshipKey, "$1"));
		
		
		try
		{
			scanner = new Scanner(file);
			String nextLine = scanner.nextLine();
			while(scanner.hasNextLine())
			{
				Matcher rightObject = findObject.matcher(nextLine);
				if(rightObject.find())
				{
					Matcher next = nextObject.matcher(nextLine);
					if(next.find())
					{
						break;
					}
				}
			}
			
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			scanner.close();
		}
		
		
		return null;
	}

	

	public static List<String> getWorkmanshipRace(RaceDnd raceDnd)
	{
		List<String> pool = new ArrayList<>();
		Scanner classScanner = null;
		Pattern findNameSkills = Pattern.compile(featureKey);
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

	static void createFeature(CharacterDnd character, String skill) 
	{
		
		if(!character.getWorkmanship().getMyFeatures().contains(new Feature(skill)))
		{
			character.getWorkmanship().getMyFeatures().add(new Feature(skill));
		}
	}

	static void createTrait(CharacterDnd character, String trait) 
	{
		if(!character.getWorkmanship().getMyFeatures().contains(new Trait(trait)))
		{
			character.getWorkmanship().getMyFeatures().add(new Trait(trait));
		}
		
	}

	static void createPossession(CharacterDnd character, String possession) 
	{
		if(!character.getWorkmanship().getMyPossessions().contains(new Possession(possession)))
		{
			character.getWorkmanship().getMyPossessions().add(new Possession(possession));
		}
	}

	static void createSpell(CharacterDnd character, String spell) 
	{
		if(!character.getWorkmanship().getMySpells().contains(new Spell(spell)))
		{
			character.getWorkmanship().getMySpells().add(new Spell(spell));
		}
	}

	static File[] getWorkmanship() 
	{
		return workmanship;
	}

}