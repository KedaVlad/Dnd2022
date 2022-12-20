package com.dnd.botTable.actions;

import com.dnd.botTable.Action;
import com.dnd.dndTable.ObjectDnd;

public class RegistrateAction extends Action
{

	private static final long serialVersionUID = 1L;
	
	private ObjectDnd target;
	
	public static RegistrateAction create(String name, ObjectDnd target)
	{
		RegistrateAction action = new RegistrateAction();
		action.name = name;
		action.key = target.key();
		action.target = target;
		return action;
		
	}
	
	@Override
	protected String[][] buildButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Action continueAction(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean hasButtons() {
		// TODO Auto-generated method stub
		return false;
	}

	public ObjectDnd getTarget() {
		return target;
	}

}
