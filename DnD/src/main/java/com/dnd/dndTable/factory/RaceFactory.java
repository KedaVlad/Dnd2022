package com.dnd.dndTable.factory;

import java.io.File;

import com.dnd.Source;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;



public class RaceFactory implements Source, Factory 
{
	private static final File dirRace = new File(raceSource);
	private static File subRace;
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void create(CharacterDnd character, String raceName, String subRace) 
	{
		character.setRaceDnd(refactor(character, new RaceDnd(raceName,subRace)));
	}

	private static RaceDnd refactor(CharacterDnd character, RaceDnd raceDnd)
	{
		WorkmanshipFactory.getWorkmanship(character, WorkmanshipFactory.getWorkmanshipRace(raceDnd));

		return raceDnd;
	}

	public static String[] getRaceArray() 
	{
		String[] allRaces = dirRace.list();
		return allRaces;
	}

	public static String[] getSubRaceArray(String race)
	{
		subRace = new File(raceSource + race);
		String[] allSubRace = subRace.list();
		return allSubRace;
	}
	
	public static String getObgectInfo(ObjectDnd objectDnd) {
		// TODO Auto-generated method stub
		return "";
	}

}
