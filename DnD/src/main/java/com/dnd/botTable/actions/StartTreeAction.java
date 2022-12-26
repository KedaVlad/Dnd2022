package com.dnd.botTable.actions;

import com.dnd.botTable.Action;

public class StartTreeAction extends Action {


	public static StartTreeAction create(long key)
	{
		StartTreeAction action = new StartTreeAction();
		action.key = key;
		return action;
		
	}
	
	private static final long serialVersionUID = 1L;

	@Override
	protected String[][] buildButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action continueAction(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean hasButtons() {
		// TODO Auto-generated method stub
		return false;
	}

}
