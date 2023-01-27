package com.dnd.dndTable.factory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.ButtonName;
import com.dnd.KeyWallet;
import com.dnd.Source;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.ClassDnd;
import com.dnd.dndTable.factory.inerComands.InerComand;

abstract class ClassFactory implements Source, KeyWallet, ButtonName
{
	private final static File dirClass = new File(classSource);;
	private static File dirArchetype;
	static final long KEY = CLASS_FACTORY_K;
	final static int END = 4;

	
	public static Act execute(Action action)
	{
		int condition = 0;
		if(action.getAnswers() != null) condition = action.getAnswers().length;
		switch(condition)
		{
		case 0:
			return  startCreate();
		case 1:
			return	chooseArchetype(action);
		case 2:
			return chooseLvl(action);
		case 3:
			return checkCondition(action);
		}
		return null;
	}

	private static Act startCreate()
	{
		return Act.builder()
				.name("CreateClass")
				.text("What is your class?")
				.action(Action.builder()
						.location(Location.FACTORY)
						.key(KEY)
						.buttons(getClassArray())
						.build())
				.returnTo(START_B)
				.build();
	}
	
	private static Act chooseArchetype(Action action)
	{
		action.setButtons(getArchetypeArray(action.getAnswers()[0]));
		return Act.builder()
				.name("ChooseClassArchtype")
				.text(action.getAnswers()[0] +  ", realy? Which one?")
				.action(action)
				.build();
	}

	private static Act chooseLvl(Action action)
	{
		action.setMediator();
		return Act.builder()
				.name("ChooseClassLvl")
				.text("What is your lvl?")
				.action(action)
				.build();
	}

	private static Act checkCondition(Action action)
	{
		int lvl = 0;
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(action.getAnswers()[2]);
		while (matcher.find()) 
		{
			lvl = ((Integer) Integer.parseInt(matcher.group()));
		}

		if(0 < lvl && lvl <= 20)
		{
			action.setButtons(new String[][] {{"Yeah, right"}});
			return Act.builder()
					.name("checkClassCondition")
					.text(getObgectInfo(action.getAnswers()[0], action.getAnswers()[1]) + "\nIf not, select another option above." )
					.action(action)
					.build();
		}
		else
		{
			action.getAnswers()[2] = 1 + "";
			action.setButtons(new String[][] {{"Okey"}});
			return Act.builder()
					.name("checkClassCondition")
					.text(lvl + "??? I see you're new here. Let's start with lvl 1.\nAre you satisfied with this option?/n"
							+  getObgectInfo(action.getAnswers()[0], action.getAnswers()[1]) + "\nIf not, select another option above." )
					.action(action)
					.build();
		}
	}

	static void finish(Action action, CharacterDnd character) 
	{
		try
		{
			String className = action.getAnswers()[0];
			String archetype = action.getAnswers()[1];
			int lvl = ((Integer) Integer.parseInt(action.getAnswers()[2]));
			ClassDnd clazz = Json.fromFileJson(classSource + className + "\\" + archetype + ".json", ClassDnd.class);
			clazz.setLvl(lvl);
			character.setClassDnd(clazz);
			character.addMemoirs(getObgectInfo(className, archetype));
			for(int i = 0; i <= lvl; i++)
			{
				for(InerComand comand: character.getClassDnd().getGrowMap()[i])
				{	
					ScriptReader.execute(character, comand);
					comand = null;
				}
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	private static String[][] getClassArray() 
	{
		String[] all = dirClass.list();
		String[][] allClasses = new String[all.length][1];
		for(int i = 0; i < all.length; i++)
		{
			allClasses[i][0] = all[i];
		}
		return allClasses;
	}

	private static String[][] getArchetypeArray(String className) 
	{
		dirArchetype = new File(classSource + className);
		String[] all = dirArchetype.list();
		String[][] allArchetypes = new String[all.length][1];
		for(int i = 0; i < all.length; i++)
		{
			allArchetypes[i][0] = all[i].replaceAll("([a-zA-Z]*)(.json)","$1");
		}
		return allArchetypes;
	}

	private static String getObgectInfo(String classDnd, String archetype) {

		String answer = classDnd + archetype;
		return answer;
	}

}
