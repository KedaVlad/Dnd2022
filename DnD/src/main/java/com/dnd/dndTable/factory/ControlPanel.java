package com.dnd.dndTable.factory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Log.Place;
import com.dnd.Source;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;

public class ControlPanel implements KeyWallet, Source {

	private File myCharactersDir;
	private String classBeck;
	private String archetypeBeck;
	private int classLvl;
	private String race;
	private String subRace;	
	private File subRaceDir;


	public enum ObjectType
	{
		CLASS, ARCHETYPE, RACE, SUBRACE, ITEM, STATS;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	public CharacterDnd createCharecter(String name)
	{
		return CharacterFactory.create(name, myCharactersDir);
	}

	public void save(CharacterDnd character)
	{
		CharacterFactory.save(character, myCharactersDir);
	}

	public CharacterDnd load(String name)
	{
		return CharacterFactory.load(name, myCharactersDir);
	}

	public void delete(CharacterDnd character)
	{
		CharacterFactory.delete(character, myCharactersDir);
	}

	public void createClass(CharacterDnd character)
	{
		ClassFactory.create(character, classBeck, classLvl, archetypeBeck);
	}
	
	public void createRace(CharacterDnd character)
	{
		RaceFactory.create(character, race, subRace);
	}

	public String[] getArray(ObjectType type)
	{
		switch(type)
		{
		case CLASS:
			return ClassFactory.getClassArray();
		case ARCHETYPE:
			return ClassFactory.getArchetypeArray(classBeck);
		case RACE:
			return RaceFactory.getRaceArray();
		case SUBRACE:
			return RaceFactory.getSubRaceArray(race);
		default:
			Log.add("getArray(ERROR not valid ObjectType", Place.ERROR , Place.DND, Place.CONTROLPANEL);
			break;
		}
		return null;
	}

    public String getObjectInfo(ObjectType type) 
    {
		switch(type)
		{
		case CLASS:
				return ClassFactory.getObgectInfo(classBeck, archetypeBeck);
			
		case RACE:
			return RaceFactory.getObgectInfo(race, subRace);
		case STATS:
			return CharacterFactory.getCharacterStatInfo();
		default:
			Log.add("getObjectInfo(ERROR not valid ObjectType", Place.ERROR , Place.DND, Place.CONTROLPANEL);
			break;
		}
		return null;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     public void cleanLocalData()
    {
    	classBeck = null;
    	archetypeBeck = null;
    	classLvl = 0;
    	race = null;
    	subRace = null;	
    	subRaceDir = null;
    }
    
	public void setMyCharactersDir(String userName) 
	{
		myCharactersDir = new File(userSource + userName);
		if(!myCharactersDir.exists()) {
			myCharactersDir.mkdir();
		}	
	}

	public File getMyCharactersDir() 
	{

		return myCharactersDir;
	}

	public String getClassBeck() 
	{
		return classBeck;
	}

	public void setClassBeck(String classBeck) 
	{
		
		Log.add(this.classBeck + " " + classBeck, Place.CONTROLPANEL, Place.COMMAND);
		this.classBeck = classBeck;
	}

	public String getArchetypeBeck() 
	{
		return archetypeBeck;
	}

	public void setArchetypeBeck(String archerypeBeck) 
	{
		this.archetypeBeck = archerypeBeck;
	}

	public String getSubRace()
	{
		return subRace;
	}

	public void setSubRace(String subRace)
	{
		this.subRace = subRace;
	}

	public String getRace() 
	{
		return race;
	}

	public void setRace(String race)
	{
		this.race = race;
	}

	public int getClassLvl()
	{
		return classLvl;
	}

	public void setClassLvl(int classLvl)
	{
		this.classLvl = classLvl;
	}

	public File getSubRaceDir() 
	{
		return subRaceDir;
	}

	public void setSubRaceDir(File subRaceDir) 
	{
		this.subRaceDir = subRaceDir;
	}

}
