package com.dnd.dndTable.factory;

import java.io.File;

import com.dnd.Log;
import com.dnd.Source;
import com.dnd.Log.Place;
import com.dnd.dndTable.*;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.*;

public class ClassFactory implements Factory,Source 
{

	private final static File dirClass = new File(classSource);;
	private static File dirArchetype;

	public static void create(CharacterDnd character, String className, int lvl, String archetype) 
	{
		Log.add("Class " + className +" " + archetype + " create", Place.FACTORY, Place.CLASS);
		switch(className)
		{
		case "Artificer":
			character.setClassDnd(refactor(className, archetype, new Rogue(lvl, archetype)));
			
		case "Barbarian":
			character.setClassDnd(refactor(className, archetype, new Barbarian(lvl, archetype)));
			break;
			
		case "Bard":
			character.setClassDnd(refactor(className, archetype, new Bard(lvl, archetype)));
			break;
			
		case "Blood Hunter":
			character.setClassDnd(refactor(className, archetype, new BloodHunter(lvl, archetype)));
			break;
			
		case "Cleric":
			character.setClassDnd(refactor(className, archetype, new Cleric(lvl, archetype)));
			break;
			
		case "Druid":
			character.setClassDnd(refactor(className, archetype, new Druid(lvl, archetype)));
			break;
			
		case "Fighter":
			character.setClassDnd(refactor(className, archetype, new Fighter(lvl, archetype)));
			break;
			
		case "Monk":
			character.setClassDnd(refactor(className, archetype, new Monk(lvl, archetype)));
		
			
		case "Rogue":
			character.setClassDnd(refactor(className, archetype, new Rogue(lvl, archetype)));
			break;
			
		case "Warlock":
			character.setClassDnd(refactor(className, archetype, new Warlock(lvl, archetype)));
			break;
			
		case "Wizard":
			character.setClassDnd(refactor(className, archetype, new Wizard(lvl, archetype)));
			break;
		}
		WorkmanshipFactory.getWorkmanship(character, WorkmanshipFactory.getWorkmanshipClass(character.getClassDnd()));
	
	}

	private static ClassDnd refactor(String className, String archetype, ClassDnd classDnd)
	{
		
		classDnd.setMainFile(new File(classSource + className + "\\" + archetype + ".txt"));
		
		return classDnd;
	}

	public static String[] getClassArray() 
	{
		String[] allClasses = dirClass.list();
		return allClasses;
	}

	public static String[] getArchetypeArray(String className) 
	{
		dirArchetype = new File(classSource + className);
		String[] allArchetypes = dirArchetype.list();
		return allArchetypes;
	}

	public static String getObgectInfo(ObjectDnd objectDnd) {
		
		ClassDnd someClass = (ClassDnd) objectDnd;
		return someClass.getLvl() + someClass.getMyArchetypeClass() + "";
	}

}
