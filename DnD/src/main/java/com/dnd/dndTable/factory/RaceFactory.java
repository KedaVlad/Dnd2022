package com.dnd.dndTable.factory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Source;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.FactoryAction;
import com.dnd.botTable.actions.FinalAction;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.RaceDnd;
import com.dnd.dndTable.factory.inerComands.InerComand;

abstract class RaceFactory implements Source
{
	private final static File dirRace = new File(raceSource);
	private static File dirSubRace;
	static final long key = 987976965;
	
	public static Action startCreate()
	{
		String name = "CreateRace";
		String text = "From what family you are?";
		return  FactoryAction.create(name, key, false, text, getRaceArray());
	}
	
	public static Action execute(FactoryAction action)
	{
		switch(action.getLocalData().size())
		{
		case 1:
			return chooseSubRace(action);
		case 2:
			return checkCondition(action);
		}
		return null;
	}
	
	private static Action chooseSubRace(FactoryAction action) {
		action.setName("ChooseSubRace");
		action.setText(action.getLocalData().get(0) + "? More specifically?");
		action.setNextStep(getSubRaceArray(action.getLocalData().get(0)));
		return action;
		
	}
	
	private static Action checkCondition(FactoryAction action) {
		action.setName("checkCondition");
		action.setText(getObgectInfo(action.getLocalData().get(0), action.getLocalData().get(1)));
		action.setNextStep(new String[][] {{"Yeah, right"}});
		return FinalAction.create(action);
	}
	
	static void finish(FinalAction action, CharacterDnd character) 
	{
		String raceName = action.getLocalData().get(0);
		String subRace = action.getLocalData().get(1);
		try
		{
			character.setRaceDnd(Json.fromFileJson(raceSource + raceName + "\\" + subRace, RaceDnd.class));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		for(InerComand comand: character.getMyRace().getSpecials())
		{
			ScriptReader.execute(character, comand);
		}	
	}
	
	public static void create(CharacterDnd character, String raceName, String subRace) 
	{
		try
		{
			character.setRaceDnd(Json.fromFileJson(raceSource + raceName + "\\" + subRace, RaceDnd.class));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		for(InerComand comand: character.getMyRace().getSpecials())
		{
			ScriptReader.execute(character, comand);
		}	
	}
	
	public static String[][] getRaceArray() 
	{
		String[] all = dirRace.list();
		List<String[]> buttons = new ArrayList<>();
		
		for(int i = 1; i <= all.length; i += 3)
		{
			if(((i + 1) > all.length)
					&&((i + 2) > all.length)) 
			{
				buttons.add(new String[]{all[i - 1]});	
			}
			else if((i + 2) > all.length)
			{
				buttons.add(new String[]{all[i - 1], all[i]});	
			}
			else
			{
				buttons.add(new String[]{all[i - 1], all[i], all[i+1]});		
			}
		}
		
		String[][] allRaces = buttons.toArray(new String[buttons.size()][]);
		
		return allRaces;
	}
	
	public static String[][] getSubRaceArray(String race)
	{
		dirSubRace = new File(raceSource + race);
		
        String[] all = dirSubRace.list();
		
		String[][] allRaces = new String[all.length][1];
		for(int i = 0; i < all.length; i++)
		{
			allRaces[i][0] = all[i];
		}
		
		return allRaces;
	}
	
	public static String getObgectInfo(String race, String subRace) {
		
		return race + subRace;
	}

}
