package com.dnd.botTable.actions;

import com.dnd.Log;
import com.dnd.botTable.Action;

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
		return answer;
	}

	@Override
	public Action continueAction(String key) 
	{

		Log.add(this);
		String regex = "(\\d+)([a-zA-Z]+)";
		String target = key.replaceAll(regex, "$1");
		Log.add(target);
		String callback = key.replaceAll(regex, "$2");
		Log.add(callback);

		for(Action act: this.pool)
		{
			if((act.getKey() + "").equals(target))
			{
				if(act instanceof HeroAction)
				{
					HeroAction action = (HeroAction)act;
					return action.continueAction(callback);
				}
				else if(act instanceof BotAction)
				{
					BotAction action = (BotAction)act;
					return action.continueAction(callback);
				}
				else if(act instanceof RegistrateAction)
				{
					RegistrateAction action = (RegistrateAction) act;
					return action.continueAction(callback);
				}
			}
		}
		Log.add("BED BED BED ARRAY ACTION BED CONTIN");
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
