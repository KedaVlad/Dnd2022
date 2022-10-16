package com.dnd.dndTable.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.dnd.Log;
import com.dnd.Source;
import com.dnd.Log.Place;
import com.dnd.dndTable.*;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.*;

abstract class ClassFactory implements Source 
{

	private final static File dirClass = new File(classSource);;
	private static File dirArchetype;

	static void create(CharacterDnd character, String className, int lvl, String archetype) 
	{
		Log.add("Class " + className +" " + archetype + " create", Place.FACTORY, Place.CLASS);
		switch(className)
		{
		case "Artificer":
			refactor(character, className, archetype, new Rogue(lvl, archetype));
			
		case "Barbarian":
			refactor(character, className, archetype, new Barbarian(lvl, archetype));
			break;
			
		case "Bard":
			refactor(character, className, archetype, new Bard(lvl, archetype));
			break;
			
		case "Blood Hunter":
			refactor(character, className, archetype, new BloodHunter(lvl, archetype));
			break;
			
		case "Cleric":
			refactor(character, className, archetype, new Cleric(lvl, archetype));
			break;
			
		case "Druid":
			refactor(character, className, archetype, new Druid(lvl, archetype));
			break;
			
		case "Fighter":
			refactor(character, className, archetype, new Fighter(lvl, archetype));
			break;
			
		case "Monk":
			refactor(character, className, archetype, new Monk(lvl, archetype));
			
		case "Rogue":
			refactor(character, className, archetype, new Rogue(lvl, archetype));
			break;
			
		case "Warlock":
			refactor(character, className, archetype, new Warlock(lvl, archetype));
			break;
			
		case "Wizard":
			refactor(character, className, archetype, new Wizard(lvl, archetype));
			break;
		}
	
	}

	private static void refactor(CharacterDnd character, String className, String archetype, ClassDnd classDnd)
	{
		
		classDnd.setMainFile(new File(classSource + className + "\\" + archetype + ".txt"));
		character.setClassDnd(classDnd);
		Scanner scanner = null;
		try {
			scanner = new Scanner(classDnd.getMyClassMainFile());
			String nextLine = scanner.nextLine();
			while(scanner.hasNextLine())
			{
				if(nextLine.equals(character.getLvl() + 1 + ""))
				{
					break;
				}
				ScriptReader.execute(character, nextLine);
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
	}

	static String[] getClassArray() 
	{
		String[] allClasses = dirClass.list();
		return allClasses;
	}

	static String[] getArchetypeArray(String className) 
	{
		dirArchetype = new File(classSource + className);
		String[] allArchetypes = dirArchetype.list();
		return allArchetypes;
	}

	static String getObgectInfo(String classDnd, String archetype) {
		
	String answer = classDnd + archetype;
		return answer;
	}

}
