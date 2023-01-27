package com.dnd.dndTable.creatingDndObject.workmanship;


import java.util.ArrayList;
import java.util.List;

import com.dnd.botTable.actions.PoolActions;
import com.dnd.Executor;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.BaseAction;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.dndTable.Refreshable;
import com.dnd.dndTable.creatingDndObject.workmanship.features.ActiveFeature;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;


public class Workmanship implements Refreshable, Executor
{

	private static final long serialVersionUID = -6541873819810645125L;
	private MagicSoul magicSoul;
	private List<Feature> myFeatures; 
	private List<Spell> mySpells;
	private Possessions possessions;

	public Workmanship()
	{
		myFeatures = new ArrayList<>();
		mySpells = new ArrayList<>();
		possessions = new Possessions();
	}

	@Override
	public Act execute(Action action) 
	{
		if(Executor.condition(action) == 0 && action.getObjectDnd() == null)
		{
			return ability();
		}
		else
		{
			return getMenu(action);
		}
	}

	private Act ability() 
	{
		String instruction = "Some instruction for ABILITY interface";
		String[][] buttons;	
		if(magicSoul == null)
		{
			buttons = new String[][]{{FEATURE_B, POSSESSION_B},{RETURN_TO_MENU}};
		}
		else
		{
			buttons = new String[][]{{FEATURE_B, SPELL_B, POSSESSION_B},{RETURN_TO_MENU}};
		}

		return Act.builder()
				.name(ABILITY_B)
				.text(instruction)
				.action(Action.builder()
						.buttons(buttons)
						.location(Location.CHARACTER)
						.key(key())
						.replyButtons()
						.build())
				.returnTo(MENU_B)
				.build();
	}

	private Act getMenu(Action action) 
	{
		if(action.getObjectDnd() == null)
		{
			String target = action.getAnswers()[0];
			if(target.equals(FEATURE_B))
			{
				return featureExecute(action);
			}
			else if(target.equals(POSSESSION_B))
			{
				return possessions.execute(action);
			}
			else if(target.equals(SPELL_B))
			{
				return magicSoul.execute(action);
			}
			else
			{
				return Act.builder().returnTo(MENU_B, MENU_B).build();
			}
		}
		else
		{
			if(action.getObjectDnd() instanceof Feature)
			{
				return featureExecute(action);
			}
			else if(action.getObjectDnd() instanceof Spell)
			{
				return magicSoul.execute(action);
			}
			else
			{
				return Act.builder().returnTo(MENU_B, MENU_B).build();
			}
		}
	}

	private Act featureExecute(Action action)
	{
		if(action.getObjectDnd() == null)
		{
			return featureMenu(); 
		}
		else
		{
			return featureCase(action);
		}
	}

	private Act featureMenu() 
	{
		BaseAction[][] pool = new BaseAction[myFeatures.size()][1];
		for(int i = 0; i < myFeatures.size(); i++)
		{
			Feature feature = myFeatures.get(i);
			pool[i][0] = Action.builder().name(feature.getName()).location(Location.CHARACTER).key(key()).objectDnd(feature).build();
		}
		return Act.builder()
				.name(FEATURE_B)
				.text("This is your feature. Choose some for more infotmation")
				.action(PoolActions.builder()
						.actionsPool(pool)
						.build())
				.returnTo(ABILITY_B)
				.build();
	}

	private Act featureCase(Action action) 
	{
		Feature feature = (Feature) action.getObjectDnd();
		String name = feature.getName();
		String text = name + "\n" + feature.getDescription();
		if(feature instanceof ActiveFeature)
		{
			action.setButtons(new String[][] {{"Cast"}});
			return Act.builder()
					.name(name)
					.text(text)
					.action(action)
					.build();
		}
		else
		{
			return Act.builder()
					.name(name)
					.text(text)
					.build();
		}
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
	public void refresh(Time time) 
	{
		for(Feature feature: myFeatures)
		{
			if(feature instanceof ActiveFeature)
			{
				ActiveFeature target = (ActiveFeature) feature;
				target.refresh(time);
			}
		}

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
		return ABILITY;
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


}
