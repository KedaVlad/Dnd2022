package com.dnd.botTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dnd.dndTable.creatingDndObject.CharacterDnd;

public class CharactersPool implements Serializable
{
	private static final long serialVersionUID = 1L;
	CharacterDnd actual;
	Clouds clouds;
	Map<String, CharacterDnd> savedCharacters;

	CharactersPool(List<Integer> trash)
	{
		clouds = new Clouds(trash);
		savedCharacters = new LinkedHashMap<>();
	}

	public void save() 
	{
		savedCharacters.put(actual.getName(), actual);
	}

	void delete(String name) 
	{
		savedCharacters.remove(name);
	}

	void download(String name)
	{
		if(actual != null) save();
		if(savedCharacters.containsKey(name))
		{
			actual = savedCharacters.get(name);
			this.clouds.initialize(this.actual.getCloud());
		}
	}

	public CharacterDnd getActualCharacter() 
	{
		return actual;
	}

	public void setActualCharacter(CharacterDnd actual) 
	{
		this.actual = actual;
		this.clouds.initialize(actual.getCloud());
	}

	public List<Act> getClouds() 
	{
		return clouds.clouds;
	}

	public boolean hasCloud()
	{
		return actual != null && clouds.clouds.size() > 0 && actual.getHp().getNow() > 0;
	}

}

class Clouds implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	final List<Integer> trash;
	List<Act> clouds;
	List<Act> cloudsWorked;
	
	Clouds(List<Integer> trash)
	{
		this.trash = trash;
		this.clouds = new ArrayList<>();
		this.cloudsWorked = new ArrayList<>();
	}
	
	void initialize(List<Act> clouds)
	{
		this.clouds.addAll(cloudsWorked);
		for(Act act: cloudsWorked)
		{
			this.trash.addAll(act.end());
		}
		this.cloudsWorked.clear();
		this.clouds = clouds;
	}

}
