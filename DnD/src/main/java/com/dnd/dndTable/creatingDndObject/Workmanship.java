package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.workmanship.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.google.common.math.Stats;


public class Workmanship implements Serializable 
{
	
	private static final long serialVersionUID = -6541873819810645125L;
	
	
	private List<Feature> myFeatures; 
	private List<Spell> mySpells;
	private List<Possession> myPossessions;
	
	
	public Workmanship()
	{
		myFeatures = new ArrayList<>();
		mySpells = new ArrayList<>();
		myPossessions = new ArrayList<>();
	}
	
	public List<Feature> getMyFeatures() 
	{
		return myFeatures;
	}

	public void addFeature(Feature features) 
	{
		myFeatures.add(features);
	}

	public void deleteFeature(String name)
	{
		for(Feature feature: myFeatures)
		{
			if(feature.getName().equals(name)) 
			{
				myFeatures.remove(feature);
				break;
			}
		}
	}
	
	public List<Spell> getMySpells() 
	{
		return mySpells;
	}

	public void addSpell(Spell spells) 
	{
		mySpells.add(spells);
	}
	
	public void deleteSpell(String name)
	{
		for(Spell spell: mySpells)
		{
			if(spell.getName().equals(name)) 
			{
				mySpells.remove(spell);
				break;
			}
		}
	}

	public List<Possession> getMyPossessions() 
	{
		return myPossessions;
	}
	
	public void addPossession(Possession possession) 
	{
		myPossessions.add(possession);
	}
	
	public void deletePossession(String name)
	{
		for(Possession possession: myPossessions)
		{
			if(possession.getName().equals(name)) 
			{
				myPossessions.remove(possession);
				break;
			}
		}
	}
	
}
