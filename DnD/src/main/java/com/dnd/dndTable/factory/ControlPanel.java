package com.dnd.dndTable.factory;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Source;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.CharacterDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.rolls.Dice;

public class ControlPanel implements KeyWallet, Source, Serializable {

	private static final long serialVersionUID = -231330030795056098L;
	private File mainFile;
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
			Log.add("getArray(ERROR not valid ObjectType");
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

			String godGift = Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20() + ", " + Dice.d20();

			String answer =  "Now let's see what you have in terms of characteristics.\r\n"
					+ "\r\n"
					+ "Write the value of the characteristics in order: Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma.\r\n"
					+ "1.Each stat cannot be higher than 20.\r\n"
					+ "2. Write down stats without taking into account buffs from race / class.\r\n"
					+ "\r\n"
					+ "Use the random god gift in the order you want your stats to be.\r\n"
					+ "\r\n" + "\r\n" + godGift + "\r\n"
					+ "\r\n"
					+ "Or write down those values that are agreed with your game master.";
			return answer;
			
		default:
			Log.add("getObjectInfo(ERROR not valid ObjectType");
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

	public String getClassBeck() 
	{
		return classBeck;
	}

	public void setClassBeck(String classBeck) 
	{
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

	public File getMainFile() {
		return mainFile;
	}

	public void setMainFile(File mainFile) {
		this.mainFile = mainFile;
	}

}
