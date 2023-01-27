package com.dnd.dndTable.factory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dnd.ButtonName;
import com.dnd.KeyWallet;
import com.dnd.Source;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.RaceDnd;
import com.dnd.dndTable.factory.inerComands.InerComand;

abstract class RaceFactory implements Source, ButtonName, KeyWallet
{
	private final static File dirRace = new File(raceSource);
	private static File dirSubRace;
	final static int END = 3;
	static final long KEY = RACE_FACTORY_K;
	
	
	public static Act execute(Action action)
	{
		int condition = 0;
		if(action.getAnswers() != null) condition = action.getAnswers().length;
		switch(condition)
		{
		case 0:
			return startCreate();
		case 1:
			return chooseSubRace(action);
		case 2:
			return checkCondition(action);
		}
		return null;
	}

	private static Act startCreate()
	{
		return Act.builder()
				.name("CreateRace")
				.text("From what family you are?")
				.action(Action.builder()
						.location(Location.FACTORY)
						.key(KEY)
						.buttons(getRaceArray())
						.build())
				.returnTo(START_B)
				.build();
	}
	
	private static Act chooseSubRace(Action action) 
	{
		action.setButtons(getSubRaceArray(action.getAnswers()[0]));
		return Act.builder()
				.name("ChooseSubRace")
				.text(action.getAnswers()[0] + "? More specifically?")
				.action(action)
				.build();		
	}
	
	private static Act checkCondition(Action action) 
	{
		action.setButtons(new String[][] {{"Yeah, right"}});
		return Act.builder()
				.name("checkCondition")
				.text(getObgectInfo(action.getAnswers()[0], action.getAnswers()[1]) + "\nIf not, select another option above.")
				.action(action)
				.build();
	}
	
	static void finish(Action action, CharacterDnd character) 
	{
		String raceName = action.getAnswers()[0];
		String subRace = action.getAnswers()[1];
		try
		{
			character.setRaceDnd(Json.fromFileJson(raceSource + raceName + "\\" + subRace + ".json", RaceDnd.class));
			character.addMemoirs(getObgectInfo(raceName, subRace));
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

	private static String[][] getRaceArray() 
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
	
	private static String[][] getSubRaceArray(String race)
	{
		dirSubRace = new File(raceSource + race);
		
        String[] all = dirSubRace.list();
		
		String[][] allRaces = new String[all.length][1];
		for(int i = 0; i < all.length; i++)
		{
			allRaces[i][0] = all[i].replaceAll("([a-zA-Z]*)(.json)","$1");
		}
		
		return allRaces;
	}
	
	private static String getObgectInfo(String race, String subRace) {
		
		return race + subRace;
	}

}
