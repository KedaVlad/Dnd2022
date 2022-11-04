package com.dnd.dndTable.factory;

import java.io.File;
import java.io.IOException;

import com.dnd.Source;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;
import com.dnd.localData.Json;



abstract class RaceFactory implements Source
{
	private final static File dirRace = new File(raceSource);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	
	public static String[] getRaceArray() 
	{
		String[] allRaces = dirRace.list();
		return allRaces;
	}

	public static String[] getSubRaceArray(String race)
	{
		File subRace = new File(raceSource + race);
		String[] allSubRace = subRace.list();
		return allSubRace;
	}
	
	public static String getObgectInfo(String race, String subRace) {
		// TODO Auto-generated method stub
		return race + subRace;
	}

}
