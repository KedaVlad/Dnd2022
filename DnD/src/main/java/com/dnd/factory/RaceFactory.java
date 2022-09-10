package com.dnd.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.Source;
import com.dnd.creatingCharacter.raceDnd.RaceDnd;


public class RaceFactory implements Source, Factory 
{

	private static RaceDnd raceSubgect;
	private static String archetypeBeck;
	private static File dirRace;
	
	public static RaceDnd create(String raceName) 
	{
		return refactor(new RaceDnd(raceName));
	}
	
	private static RaceDnd refactor(RaceDnd raceDnd)
	{
		WorkmanshipFactory.getWorkmanship(getWorkmanshipRace(raceDnd));
		
		return raceDnd;
	}
	
	public static List<String> getWorkmanshipRace(RaceDnd raceDnd)
	{
		List<String> pool = new ArrayList<>();
		Scanner classScanner = null;
		Pattern findNameSkills = Pattern.compile("*[a-zA-Z]");
		Pattern findNameSpells = Pattern.compile(">[a-zA-Z]");
		Pattern findNamePossession = Pattern.compile("-[a-zA-Z]");
		try {
			classScanner = new Scanner(new File(raceSource + raceDnd.getRaceName()));
			while(classScanner.hasNextLine()) {
				String workmanship = classScanner.nextLine();
				Matcher rightSkill = findNameSkills.matcher(workmanship);
				Matcher rightSpell = findNameSpells.matcher(workmanship);
				Matcher rightPossession = findNamePossession.matcher(workmanship);
				if(rightSkill.find()||rightSpell.find()||rightPossession.find()) 
				{
					pool.add(workmanship);
				} 
			} 
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {
			classScanner.close();
		}
		return pool;
	}
	
	public static String[] getRaceArray() 
	{
		dirRace = new File(raceSource);
		String[] allRaces = dirRace.list();
		return allRaces;
	}
	public static String[] getSubRaceArray()
	{
		File subRace = new File(raceSource + archetypeBeck);
		String[] allSubRace = subRace.list();
		return allSubRace;
	}

	public static String getArcherypeBeck() {
		return archetypeBeck;
	}

	public static void setArcherypeBeck(String archerypeBeck) {
		RaceFactory.archetypeBeck = archerypeBeck;
	}

	public static RaceDnd getRaceSubgect() {
		return raceSubgect;
	}

	public static void setRaceSubgect(RaceDnd raceSubgect) {
		RaceFactory.raceSubgect = raceSubgect;
	}
	
	
	
}
