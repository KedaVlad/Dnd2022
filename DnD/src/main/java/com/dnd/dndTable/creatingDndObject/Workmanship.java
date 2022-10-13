package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.skills.Feature;
import com.dnd.dndTable.creatingDndObject.skills.Possession;
import com.dnd.dndTable.creatingDndObject.skills.Spell;


public class Workmanship implements Serializable 
{
	
	private static final long serialVersionUID = -6541873819810645125L;
	
	private int profisiency;
	
	private List<Feature> myFeatures; 
	private List<Spell> mySpells;
	private List<Possession> myPossessions;
	
	
	void giveProfisiencyToStats(Stats stats)
	{
		for(Possession possession:myPossessions)
		{
			for(int i = 0; i < stats.getSkills().size(); i++)
			{
				if(stats.getSkills().get(i).name.equals(possession.getName()))
				{
					if(stats.getSkills().get(i).prof != profisiency)
					{
						stats.getSkills().get(i).setProfBuf(profisiency);
					}	
				}
			}
			for(int i = 0; i < stats.getSaveRolls().size(); i++)
			{
				if(stats.getSaveRolls().get(i).name.equals(possession.getName()))
				{
					if(stats.getSaveRolls().get(i).prof != profisiency)
					{
						stats.getSaveRolls().get(i).setProfBuf(profisiency);
					}
				}
			}
		}
	}
	
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

	public int getProfisiency() {
		return profisiency;
	}

	public void setProfisiency(int profisiency) {
		this.profisiency = profisiency;
	}
	
}
