package com.dnd.botTable.actions;

import com.dnd.botTable.Action;

public class ArrayAction extends BotAction
{
	private static final long serialVersionUID = 1L;

	private BotAction[] pool;

	public BotAction[] getPool()
	{
		return pool;
	}
	
	public static ArrayAction create(String name, long key, BotAction[] pool)
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

		String regex = "(.+)([a-zA-Z]+)";
		String target = key.replaceAll(regex, "$1");
		String callback = key.replaceAll(regex, "$2");
		BotAction action = null;
		for(BotAction act: this.pool)
		{
			if((act.getKey() + "").equals(target))
			{
				action = act;
				break;
			}
		}
		
		return action.continueAction(callback);
	}
	
}
