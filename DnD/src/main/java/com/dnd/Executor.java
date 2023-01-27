package com.dnd;

import java.io.Serializable;

import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;

public interface Executor extends KeyWallet, Serializable, ButtonName
{
	abstract long key();
	abstract Act execute(Action action);
	
	static int condition(Action action)
	{
		int condition = 0;
		if(action.getAnswers() != null) 
			{
			condition = action.getAnswers().length;
			}
		return condition;
	}
}
