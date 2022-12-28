package com.dnd.botTable.actions.dndAction;

import com.dnd.botTable.Action;
import com.dnd.dndTable.ObjectDnd;

public class RegistrateAction extends StartTreeAction
{

	private static final long serialVersionUID = 1L;
	
	protected ObjectDnd target;
	
	public static RegistrateAction create(String name, ObjectDnd target)
	{
		RegistrateAction action = new RegistrateAction();
		action.setName(name);
		action.key = target.key();
		action.target = target;
		return action;
		
	}
	
	public ObjectDnd getTarget() {
		return target;
	}

}
