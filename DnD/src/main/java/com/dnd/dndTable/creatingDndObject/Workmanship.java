package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.Action;
import com.dnd.botTable.actions.BotAction;
import com.dnd.botTable.actions.WrappAction;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.dndTable.ActionObject;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Mechanics;
import com.google.common.math.Stats;


public class Workmanship implements Serializable, Refreshable, ObjectDnd 
{

	private static final long serialVersionUID = -6541873819810645125L;
	public final static long key = 348954892;
	private MagicSoul magicSoul;
	private List<Feature> myFeatures; 
	private List<Spell> mySpells;
	private Possessions possessions;

	public Action ability() 
	{
		String instruction = "Some instruction for ABILITY interface";
		Action[][] pool = null;
		if(magicSoul == null)
		{
			pool = new Action[][]{ 
				{
					getFeatureMenu().returnTo("Ability"),
					getPossessionMenu().returnTo("Ability"), 
				},
				{
					BotAction.create("RETURN TO MENU", NO_ANSWER, true, false, null, null)
				}
			};
		}
		else
		{
			pool = new Action[][]{ 
				{
					getFeatureMenu().returnTo("Ability"),
					getPossessionMenu().returnTo("Ability"), 
					magicSoul.getSpellMenu().returnTo("Ability")
				},
				{
					BotAction.create("RETURN TO MENU", NO_ANSWER, true, false, null, null)
				}
			};
		}
		return WrappAction.create("Ability", key, instruction, pool).replyButtons();
	}



	WrappAction startAction(ObjectDnd object)
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

	private WrappAction fetureAction(Feature object) {

		return null;

	}

	WrappAction execute(WrappAction action)
	{
		return null;
	}

	public Workmanship()
	{
		myFeatures = new ArrayList<>();
		mySpells = new ArrayList<>();
		possessions = new Possessions();
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

	public Possessions getPossessions() 
	{
		return possessions;
	}
	
	@Override
	public long key() {
		return key;
	}



	public Action getFeatureMenu() {

		String name = "FEATURES";
		String text = "This is your feature. Choose some for more infotmation";
		Action[][] buttons = new Action[myFeatures.size()][1];
		for(int i = 0; i < myFeatures.size(); i++)
		{
			Feature feature = myFeatures.get(i);
			buttons[i][0] = RegistrateAction.create(feature.getName(),feature);
		}
		return WrappAction.create(name, key, text, buttons);
	}

	public Action featureCase(Feature object) {

		String name = object.getName();
		String text = name + "\n" + object.getDescription();
		return WrappAction.create(name, key, text, null);
	}

	
	public Action getPossessionMenu() 
	{
		String name = "POSSESSIONS";
		String text = "This is your possessions. \n";

		for(Possession possession: possessions.getPossessions())
		{
			text += possession.toString() + "\n";
		}
		return WrappAction.create(name, key, text, null);
	}

		
	public MagicSoul getMagicSoul() 
{
		return magicSoul;
	}

	public void setMagicSoul(MagicSoul magicSoul) 
	{
		this.magicSoul = magicSoul;
	}

	@Override
	public void refresh(Time time) {
		// TODO Auto-generated method stub

	}


}
