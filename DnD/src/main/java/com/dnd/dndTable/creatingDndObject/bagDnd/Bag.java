package com.dnd.dndTable.creatingDndObject.bagDnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.Executor;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.botTable.actions.BaseAction;
import com.dnd.botTable.actions.PoolActions;
import com.dnd.dndTable.creatingDndObject.CarryingStuff;

public class Bag implements Serializable, Executor
{

	private static final long serialVersionUID = -3894341880184285889L;
	private static final String WEAR = "WEAR";
	private static final String THROW_OUT = "THROW OUT";
	private static final String TOP_UP = "TOP UP";
	private static final String PREPEAR = "PREPEAR";
	private List<Items> insideBag;
	private CarryingStuff carrying;

	public void add(Items item)
	{
		item.setUsed(false);
		insideBag.add(item);
	}

	public Bag() 
	{
		insideBag = new ArrayList<Items>();
	}

	@Override
	public Act execute(Action action) 
	{
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
		BaseAction[][] buttons;
		String text;
		if(insideBag.size() == 0)
		{
			text = "Yor bag is empty";
			buttons = new BaseAction[][]
					{{Action.builder().name("ADD ITEM").location(Location.FACTORY).key(ITEM_FACTORY_K).build()}};
		}
		else
		{	
			text = "Choose item";
			buttons = new BaseAction[insideBag.size()+1][];
			buttons[0] = new BaseAction[] {Action.builder().name("ADD ITEM").location(Location.FACTORY).key(ITEM_FACTORY_K).build()};
			for(int i = 1; i < buttons.length; i++)
			{
				buttons[i] = new BaseAction[] {Action.builder()
						.name(insideBag.get(i-1).getName())
						.location(Location.CHARACTER)
						.key(key())
						.objectDnd(insideBag.get(i-1))
						.build()};
			}
		}
		return Act.builder()
				.name(BAG_B)
				.text(text)
				.action(PoolActions.builder()
						.actionsPool(buttons)
						.build())
				.build();
	}

	private Act targetMenu(Action action) 
	{
		Items item = (Items) action.getObjectDnd();
		if(item instanceof Armor)
		{
			return armorMenu(action);
		}
		else if(item instanceof Weapon)
		{
			return weaponMenu(action);
		}
		else if(item instanceof Ammunition)
		{
			return ammunitionMenu(action);
		}
		else
		{
			return itemMenu(action);
		}
	}

	private Act change(Action action)
	{
		Items target = (Items) action.getObjectDnd();
		String answer = action.getAnswers()[0];
		if(answer.contains(THROW_OUT))
		{
			insideBag.remove((Items)target);
		}
		else if(answer.equals(WEAR))
		{
			insideBag.remove(target);
			target.setUsed(true);
			carrying.add((Armor) target);
		}
		else if(answer.equals(PREPEAR))
		{
			Weapon weapon = (Weapon) target;
			insideBag.remove(weapon);
			target.setUsed(true);
			carrying.add(weapon);
		}
		else if(answer.equals(TOP_UP))
		{
			if(action.getAnswers().length > 1)
			{
				Ammunition ammunition = (Ammunition) target;
				String valueInString = action.getAnswers()[1];
				int value = 0;
				Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
				Matcher matcher = pat.matcher(valueInString);
				while (matcher.find()) 
				{
					value = ((Integer) Integer.parseInt(matcher.group()));
				}
				if(valueInString.contains("-")) value = value *-1;
				ammunition.addValue(value);
			}
			else
			{
				action.setMediator();
				return Act.builder().name(TOP_UP).text("How many?(Write)").action(action).build();
			}

		}
		return Act.builder().returnTo(STUFF_B, BAG_B).build();
	}


	private Act itemMenu(Action action)
	{
		Items item = (Items) action.getObjectDnd();
		action.setButtons(new String[][] {{THROW_OUT}});
		return Act.builder()
				.name(item.getName())
				.text(item.getDescription())
				.action(action)
				.build();
	}

	private Act armorMenu(Action action)
	{
		Armor armor = (Armor) action.getObjectDnd();
		action.setButtons(new String[][] {{WEAR, THROW_OUT}});
		return Act.builder()
				.name(armor.getName())
				.text(armor.getDescription())
				.action(action)
				.build();
	}

	private Act ammunitionMenu(Action action)
	{
		Ammunition ammunition = (Ammunition) action.getObjectDnd();
		action.setButtons(new String[][] {{TOP_UP, THROW_OUT}});
		return Act.builder()
				.name(ammunition.getName())
				.text(ammunition.getDescription())
				.action(action)
				.build();
	}

	private Act weaponMenu(Action action)
	{
		Weapon weapon = (Weapon) action.getObjectDnd();
		action.setButtons(new String[][] {{PREPEAR, THROW_OUT}});
		return Act.builder()
				.name(weapon.getName())
				.text(weapon.getDescription())
				.action(action)
				.build();
	}



	public List<Items> getInsideBag() 
	{
		return insideBag;
	}


	@Override
	public long key() 
	{
		return STUFF;
	}

	public void setCarrying(CarryingStuff carrying) 
	{
		this.carrying = carrying;
	}

}
