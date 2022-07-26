package com.dnd.dndTable.factory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.Source;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.FactoryAction;
import com.dnd.botTable.actions.FinalAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.*;
import com.dnd.dndTable.factory.inerComands.InerComand;

abstract class ClassFactory implements Source 
{
	private final static File dirClass = new File(classSource);;
	private static File dirArchetype;
	static final long key = 432543654;

	public static Action startCreate(String heroName)
	{
		String name = "CreateClass";
		String text = "What is your class, " + heroName + "?";
		return FactoryAction.create(name, key, false, text, getClassArray());
	}

	public static Action execute(FactoryAction action)
	{
		switch(action.getLocalData().size())
		{
		case 1:
			return	chooseArchetype(action);
		case 2:
			return chooseLvl(action);
		case 3:
			return checkCondition(action);
		}
		return null;
	}

	private static Action chooseArchetype(FactoryAction action)
	{
		action.setName("ChooseClassArchtype");
		action.setText(action.getLocalData().get(0) +  ", realy? Which one?");
		action.setNextStep(getArchetypeArray(action.getLocalData().get(0)));
		return action;
	}

	private static Action chooseLvl(FactoryAction action)
	{
		action.setName("ChooseClassLvl");
		action.setText("What is your lvl?");
		action.setNextStep(null);
		action.setMediator(true);
		return action;
	}

	private static Action checkCondition(FactoryAction action)
	{
		int lvl = 0;
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(action.getLocalData().get(2));
		while (matcher.find()) 
		{
			lvl = ((Integer) Integer.parseInt(matcher.group()));
		}

		if(0 < lvl && lvl <= 20)
		{
			action.setName("checkCondition");
			action.setText(getObgectInfo(action.getLocalData().get(0), action.getLocalData().get(1)) + "\nIf not, select another option above." );
			action.setNextStep(new String[][] {{"Yeah, right"}});
			return FinalAction.create(action);
		}
		else
		{
			action.setName("checkCondition");
			action.setText(lvl + "??? I see you're new here. Let's start with lvl 1.\nAre you satisfied with this option?/n"
					+  getObgectInfo(action.getLocalData().get(0), action.getLocalData().get(1)) + "\nIf not, select another option above." );
			action.getLocalData().remove(0);
			action.getLocalData().add(1+"");
			action.setNextStep(new String[][] {{"Okey"}});
			return FinalAction.create(action);
		}



	}

	static void finish(FinalAction action, CharacterDnd character) 
	{

		try
		{
			String className = action.getLocalData().get(0);
			String archetype = action.getLocalData().get(1);
			int lvl = ((Integer) Integer.parseInt(action.getLocalData().get(2)));
			character.setClassDnd(Json.fromFileJson(classSource + className + "\\" + archetype, ClassDnd.class));
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
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

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

	static String[][] getClassArray() 
	{
		String[] all = dirClass.list();
		String[][] allClasses = new String[all.length][1];
		for(int i = 0; i < all.length; i++)
		{
			allClasses[i][0] = all[i];
		}
		return allClasses;
	}

	static String[][] getArchetypeArray(String className) 
	{
		dirArchetype = new File(classSource + className);
		String[] all = dirArchetype.list();
		String[][] allArchetypes = new String[all.length][1];
		for(int i = 0; i < all.length; i++)
		{
			allArchetypes[i][0] = all[i];
		}
		return allArchetypes;
	}

	static String getObgectInfo(String classDnd, String archetype) {

		String answer = classDnd + archetype;
		return answer;
	}

}
