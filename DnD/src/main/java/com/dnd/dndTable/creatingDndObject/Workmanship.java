package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.Action;
import com.dnd.botTable.actions.BotAction;
import com.dnd.botTable.actions.WrappAction;
import com.dnd.botTable.actions.dndAction.ChangeAction;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.botTable.actions.dndAction.StartTreeAction;
import com.dnd.dndTable.ActionObject;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.Spell;
import com.dnd.dndTable.creatingDndObject.workmanship.features.ActiveFeature;
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
		if(object instanceof ActiveFeature)
		{
			Action[][] pool = new Action[][]
					{
				{
					
				}
					};
			return WrappAction.create(name, key, text, null);
		}
		else
		{
			return WrappAction.create(name, key, text, null);
		}
	}

	
	public Action getPossessionMenu() 
	{
		String name = "POSSESSIONS";
		String text = "This is your possessions. \n";

		for(Possession possession: possessions.getPossessions())
		{
			text += possession.toString() + "\n";
		}
		
		Action[][] pool = new Action[][]
				{{
			StartTreeAction.create("Add new possession", POSSESSION)
				}};
		
		return WrappAction.create(name, key, text, pool);
	}
	
	Action addPossession()
	{
		String text = "If you want to add possession of some Skill/Save roll from characteristics - you can do it right by using this pattern (CHARACTERISTIC > SKILLS > Up to proficiency)\n"
				+ "If it`s possession of Weapon/Armor you shood to write ritht the type(correct spelling in Hint list)\n"
				+ "But if it concerns something else(language or metier) write as you like.";
		return ChangeAction.create(RegistrateAction.create("SomePossession", new Possession()), text, new String[][] {{"Hint list"},{"Return to abylity"}}).replyButtons().setMediator();
	}

		
	public MagicSoul getMagicSoul() 
{
		return magicSoul;
	}

	public void setMagicSoul(MagicSoul magicSoul) 
	{
		if(this.magicSoul != null)
		{
			magicSoul.getPool().setActive(this.magicSoul.getPool().getActive());
			magicSoul.getPoolCantrips().setActive(this.magicSoul.getPoolCantrips().getActive());
		}
		this.magicSoul = magicSoul;
	}

	@Override
	public void refresh(Time time) {
		// TODO Auto-generated method stub

	}



	public String info() 
	{
		String answer = "";
		if(magicSoul != null)
		{
			answer += magicSoul.info() + "\n";
		}
		
		return answer;
	}



	public Action addPossession(ChangeAction action) {
		
		String answer = action.getAnswer();
		//"Hint list"
		if(answer.equals("Return to abylity"))
		{
			return action.returnTo("Ability", "POSSESSIONS");
		}
		else if(answer.equals("Hint list"))
		{
			return BotAction.create("Hint list", NO_ANSWER, true, false, "Some list of possessions", null).returnTo("SomePossessionChange");
		}
		else
		{
			possessions.add(answer);
			return action.returnTo("Ability", "POSSESSIONS");
		}
	}


}
