package com.dnd.dndTable.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.dnd.Source;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;



abstract class RaceFactory implements Source
{
	private static File dirRace = new File(raceSource);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void create(CharacterDnd character, String raceName, String subRace) 
	{
		character.setRaceDnd(refactor(character, new RaceDnd(raceName,subRace),
				new File(raceSource + "\\" + raceName + "\\" + subRace + ".txt")));
		
		
	}

	private static RaceDnd refactor(CharacterDnd character, RaceDnd raceDnd, File file)
	{
		character.setRaceDnd(raceDnd);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			String nextLine = scanner.nextLine();
			while(scanner.hasNextLine())
			{
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
		return raceDnd;
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
