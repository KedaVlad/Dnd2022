package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.Action;
import com.dnd.botTable.actions.HeroAction;
import com.dnd.botTable.actions.RegistrateAction;
import com.dnd.dndTable.ActionObject;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Mechanics;
import com.google.common.math.Stats;


public class Workmanship implements Serializable, ObjectDnd 
{
	
	private static final long serialVersionUID = -6541873819810645125L;
	public final static long key = 348954892;
	private List<Feature> myFeatures; 
	private List<Spell> mySpells;
	private List<Possession> myPossessions;
	
	HeroAction startAction(ObjectDnd object)
	{
		if(object instanceof Feature)
		{
			if(object instanceof Mechanics)
			{
				
			}
			else
			{
				return fetureAction((Feature) object);
			}
		}
		else if(object instanceof Spell)
		{
			
		}
		
		return null;
	}
	
	private HeroAction fetureAction(Feature object) {
		
	return null;
		
	}

	HeroAction execute(HeroAction action)
	{
		return null;
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

	
	@Override
	public long key() {
		return key;
	}

	public Action getFeatureMenu() {
		
		String name = "FeatureMenu";
		String text = "This is your feature. Choose some for more infotmation";
		Action[][] buttons = new Action[myFeatures.size()][1];
		for(int i = 0; i < myFeatures.size(); i++)
		{
			Feature feature = myFeatures.get(i);
			buttons[i][0] = RegistrateAction.create(feature.getName(),feature);
		}
		return HeroAction.create(name, key, text, buttons);
	}

	public Action featureCase(Feature object) {
	
		String name = object.getName();
		String text = name + "\n" + object.getDescription();
		return HeroAction.create(name, key, text, null);
	}

	public Action getPossessionMenu() {
		String name = "FeatureMenu";
		String text = "This is your possessions. \n";
		
		for(Possession possession: myPossessions)
		{
			text += possession.getName() + "\n";
		}
		return HeroAction.create(name, key, text, null);
	}
	
}
