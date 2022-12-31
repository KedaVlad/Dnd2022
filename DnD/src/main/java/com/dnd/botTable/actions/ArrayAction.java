package com.dnd.botTable.actions;

import com.dnd.Log;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.dndAction.ChangeAction;
import com.dnd.botTable.actions.dndAction.HeroAction;
import com.dnd.botTable.actions.dndAction.PreRoll;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.botTable.actions.factoryAction.FactoryAction;
import com.dnd.botTable.actions.factoryAction.FinalAction;

public class ArrayAction extends Action
{
	private static final long serialVersionUID = 1L;

	private Action[] pool;

	public Action[] getPool()
	{
		return pool;
	}

	public static ArrayAction create(String name, long key, Action[] pool)
	{
		ArrayAction answer = new ArrayAction();
		answer.name = name;
		answer.key = key;
		answer.mainAct = true;
		answer.pool = pool;
		for(Action action: pool)
		{
			action.setName(name);
		}
		return answer;
	}

	@Override
	public Action continueAction(String key) 
	{
		
		
		String regex = "(\\d{9})(.+)";
		String target = key.replaceAll(regex, "$1");
		String callback = key.replaceAll(regex, "$2");

		for(Action act: this.pool)
		{
			if((act.getKey() + "").equals(target))
			{
				return continueAction(act, callback);
			}
		}
		Log.add("BED BED BED ARRAY ACTION BED CONTIN");
		return null;
	}
	
	private Action continueAction(Action act, String callback)
	{
		if(act instanceof ChangeAction)
		{
			ChangeAction action = (ChangeAction) act;
			return action.continueAction(callback);
		}
		else if(act instanceof PreRoll)
		{
			PreRoll action = (PreRoll)act;
			return action.continueAction(callback);
		}
		else if(act instanceof HeroAction)
		{
			HeroAction action = (HeroAction)act;
			return action.continueAction(callback);
		}
		else if(act instanceof FinalAction)
		{
			FinalAction action = (FinalAction)act;
			return action.continueAction(callback);
		}
		else if(act instanceof FactoryAction)
		{
			FactoryAction action = (FactoryAction)act;
			return action.continueAction(callback);
		}
		else if(act instanceof BotAction)
		{
			BotAction action = (BotAction)act;
			return action.continueAction(callback);
		}
		Log.add("ERROR CONTINUE ACTION IN ARRAY ACTION");
		return null;
	}

	@Override
	protected String[][] buildButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean hasButtons() {
		// TODO Auto-generated method stub
		return false;
	}

	public String toString()
	{
		String text = "";
		text += this.getName() + "\n";
		text += this.getKey() + "\n";
		for(Action action: this.pool)
		{
			text += action.getName() + " " + action.getKey();
		}
		return text;
	}

}
