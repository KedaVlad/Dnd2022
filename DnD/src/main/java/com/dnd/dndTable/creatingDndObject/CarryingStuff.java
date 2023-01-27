package com.dnd.dndTable.creatingDndObject;

import java.util.ArrayList;
import java.util.List;
import com.dnd.Executor;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.botTable.actions.BaseAction;
import com.dnd.botTable.actions.PoolActions;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor.Armors;
import com.dnd.dndTable.creatingDndObject.bagDnd.Armor.ClassArmor;
import com.dnd.dndTable.creatingDndObject.characteristic.Stat;
import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;

public class CarryingStuff implements Executor
{
	private static final long serialVersionUID = 1L;
	private static final String RETURN = "RETURN";
	private static final String ATTACK = "ATTACK";
	private boolean barbarian;
	private List<String> status;
	private List<Items> prepeared;
	private Armor[] weared = new Armor[2];
	private Stat[] stats;
	private Bag bag;
	
	{
		prepeared = new ArrayList<>();
	}
	
	@Override
	public Act execute(Action action) 
	{
		if(prepeared == null) prepeared = new ArrayList<>();
		if(action.getObjectDnd() == null)
		{
			return menu();
		}
		else if(action.getAnswers() == null)
		{
			return targetMenu(action);			
		}
		else
		{
			return change(action);
		}
	}
	
	private Act menu()
	{
		if(prepeared.size() > 0)
		{
			BaseAction[][] buttons = new BaseAction[prepeared.size()][1];
			for(int i = 0; i < prepeared.size(); i++)
			{
				buttons[i][0] = Action.builder()
						.name(prepeared.get(i).getName())
						.objectDnd(prepeared.get(i))
						.location(Location.CHARACTER)
						.key(key())
						.build();
			}
			return Act.builder()
					.name(CARRYING_STUFF_B)
					.text("Items in quick access (dressed armor, sword in scabbard, potion on belt, and so on...).")
					.action(PoolActions.builder()
							.actionsPool(buttons)
							.build())
					.build();
		}
		return Act.builder()
				.name(CARRYING_STUFF_B)
				.text("No prepeared or weared items yeat...\nFor prepearing you shood choose some item in your bag and choose \"PREPEAR\"")
				.build();
	}
	

	private Act targetMenu(Action action) 
	{
		Items item = (Items) action.getObjectDnd();
		if(item instanceof Weapon)
		{
			action.setName(RETURN);
			action.setAnswers(new String[] {RETURN});
			BaseAction[][] pool = new BaseAction[][]
					{{action},
					{Action.builder()
						.objectDnd(item)
						.name(ATTACK)
						.location(Location.CHARACTER)
						.key(ATTACK_MACHINE)
						.build()}};
			
			return Act.builder()
					.name("targetMenu")
					.text("Return to bag or attack?")
					.action(PoolActions.builder()
							.actionsPool(pool)
							.build())
					.build();
		}
		action.setButtons(new String[][] {{RETURN}});
		return Act.builder()
				.name("targetMenu")
				.text("Return to bag?")
				.action(action)
				.build();
	}

	private Act change(Action action) 
	{
		String answer = action.getAnswers()[0];
		Items item = (Items)action.getObjectDnd();
		if(answer.contains(RETURN))
		{
			prepeared.remove(item);
			bag.add(item);
		}
		return Act.builder().returnTo(STUFF_B, CARRYING_STUFF_B).build();
	}
	
	public void add(Items item)
	{
		item.setUsed(true);
		if(item instanceof Armor)
		{
			wear((Armor) item);
		}
		else
		{
			prepeared.add(item);
		}
	}
	
	private void wear(Armor armor)
	{
		if(armor.getType().getClazz().equals(ClassArmor.SHIELD))
		{
			if(weared[1] == null)
			{
				weared[1] = armor;
				prepeared.add(armor);
			}
			else
			{
				bag.add(weared[1]);
				prepeared.remove(weared[1]);
				weared[1] = armor;
			}
		}
		else
		{
			if(weared[0] == null)
			{
				weared[0] = armor;
				prepeared.add(armor);
			}
			else
			{
				bag.add(weared[0]);
				prepeared.remove(weared[0]);
				weared[0] = armor;
			}
		}
	}
	
	public String getAC() 
	{
		String answer = "AC:";
		if(weared[0] == null)
		{
			if(barbarian)
			{
				answer += (10 + stats[2].getModificator() + stats[1].getModificator());
			}
			else
			{
				answer += (10 + stats[1].getModificator());
			}
		}
		else
		{
			Armors type = weared[0].getType();
			int armor;
			if(type.getStatDependBuff() > type.getBaseArmor())
			{
				armor = type.getBaseArmor() + stats[1].getModificator();
				if(armor > type.getStatDependBuff())
				{
					answer += type.getStatDependBuff();
				}
				else
				{
					answer += armor;
				}
			}
			else
			{
				armor = type.getBaseArmor();
			}

			if(weared[1] == null)
			{
				return answer;
			}
			else
			{
				answer += "(+" + weared[1].getType().getBaseArmor() + ")";
			}
		}
		return answer;
	}

	void setStats(Stat[] stats) 
	{
		this.stats = stats;
	}
	
	@Override
	public long key() 
	{
		return STUFF;
	}

	public Armor[] getWeared()
	{
		return weared;
	}

	public void setBag(Bag bag) {
		this.bag = bag;
	}
	

}
