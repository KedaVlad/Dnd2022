package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;
import com.dnd.dndTable.factory.WorkmanshipFactory;
import com.dnd.dndTable.gameEngine.GameEngine;

public class CharacterDnd implements Serializable, ObjectDnd
{

	private static final long serialVersionUID = -7781627593661723428L;

	private String name;
	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass;
	private GameEngine gameEngine;

	private List<String> myMemoirs;


	public CharacterDnd(String name) 
	{
		this.name = name;
		gameEngine = new GameEngine();
		myMemoirs = new ArrayList<>();	
		

	}
	
	public void lvlUp(CharacterDnd character) 
	{
		myClass.setLvl(myClass.getLvl()+1);
		WorkmanshipFactory.getWorkmanship(character, WorkmanshipFactory.getWorkmanshipClass(myClass));
	}

	//Getter`s & Setter`s///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ClassDnd getClassDnd() 
	{
		return myClass;
	}

	public int getLvl() 
	{
		return myClass.getLvl();
	}

	public String getName() 
	{
		return name;
	}

	public RaceDnd getMyRace() 
	{
		return myRace;
	}

	public ClassDnd getMultiClass() 
	{
		return multiClass;
	}

	public void setMultiClass(ClassDnd multiClass) 
	{
		this.multiClass = multiClass;
	}

	public void setRaceDnd(RaceDnd raceDnd) 
	{
		this.myRace = raceDnd;
	}

	public void setClassDnd(ClassDnd classDnd) 
	{
		this.myClass = classDnd;
	}

	public String getMyMemoirs() 
	{
		
		String answer = "";
		for(String string: myMemoirs)
		{
			answer += string + "\n";
		}
		
		return answer;
	}

	public void setMyMemoirs(String memoirs) 
	{
		
		myMemoirs.add(memoirs);
	}
    
	public String getMenu() 
	{
		
		return name;
	}

	public GameEngine getGameEngine() {
		return gameEngine;
	}


}
