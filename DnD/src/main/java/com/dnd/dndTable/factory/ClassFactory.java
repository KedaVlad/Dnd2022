package com.dnd.dndTable.factory;

import java.io.File;
import java.io.IOException;

import com.dnd.Log;
import com.dnd.Source;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.*;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract class ClassFactory implements Source 
{

	private final static File dirClass = new File(classSource);;
	private static File dirArchetype;



	static void create(CharacterDnd character, String className, int lvl, String archetype) 
	{

		try
		{
			switch(className)
			{
			case "Artificer":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Artificer.class));

			case "Barbarian":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Barbarian.class));
				break;

			case "Bard":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Bard.class));
				break;

			case "Blood Hunter":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, BloodHunter.class));
				break;

			case "Cleric":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Cleric.class));
				break;

			case "Druid":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Druid.class));
				break;

			case "Fighter":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Fighter.class));
				break;

			case "Monk":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Monk.class));
				break;

			case "Rogue":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Rogue.class));
				break;

			case "Warlock":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Warlock.class));
				break;

			case "Wizard":
				character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, Wizard.class));
				break;
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		character.getClassDnd().setLvl(lvl);
		character.setLvl();
		for(int i = 0; i < lvl; i++)
		{
			for(InerComand comand: character.getClassDnd().getGrowMap().get(i))
			{	
				ScriptReader.execute(character, comand);
			}
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
