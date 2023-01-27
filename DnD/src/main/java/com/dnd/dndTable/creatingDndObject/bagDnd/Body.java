package com.dnd.dndTable.creatingDndObject.bagDnd;



import com.dnd.Executor;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.dndTable.creatingDndObject.CarryingStuff;

public class Body implements Executor 
{
	private static final long serialVersionUID = 1L;
	private Wallet wallet;
	private Bag bag;
	private CarryingStuff carrying;

	@Override
	public Act execute(Action action) 
	{
		if(action.getObjectDnd() == null)
		{
			int condition = Executor.condition(action);
			if(condition == 0)
			{
				return stuff();
			}
			else
			{
				String targetMenu = action.getAnswers()[0];
				if(targetMenu.equals(CARRYING_STUFF_B))
				{
					return carrying.execute(action);
				}
				else if(targetMenu.equals(BAG_B))
				{
					return bag.execute(action);
				}
				else if(targetMenu.equals(WALLET_B))
				{
					return wallet.execute(action);
				}
				else
				{
					return Act.builder().returnTo(MENU_B, MENU_B).build();
				}
			}
		}
		else
		{
			if(action.getObjectDnd() instanceof Items)
			{
				Items item = (Items) action.getObjectDnd();
				if(item.isUsed())
				{
					return carrying.execute(action);
				}
				else
				{
					return bag.execute(action);
				}
			}
			else
			{
				return Act.builder().returnTo(MENU_B, MENU_B).build();
			}

		}
	}

	private Act stuff() 
	{
		return Act.builder()
				.name(STUFF_B)
				.text(info())
				.action(Action.builder()
						.location(Location.CHARACTER)
						.key(key())
						.buttons(new String[][] {{CARRYING_STUFF_B, BAG_B, WALLET_B},{RETURN_TO_MENU}})
						.replyButtons()
						.build())
				.returnTo(MENU_B)
				.build();
	}

	public Body()
	{
		wallet = new Wallet();
		bag = new Bag();
		carrying = new CarryingStuff();
		carrying.setBag(bag);
		bag.setCarrying(carrying);
	}


	public String info() 
	{
		return  "Some instruction for STUFF";
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return STUFF;
	}

	public CarryingStuff getCarrying() {
		return carrying;
	}

	public void setCarrying(CarryingStuff carrying) {
		this.carrying = carrying;
	}

	public Bag getBag() {
		return bag;
	}
}
