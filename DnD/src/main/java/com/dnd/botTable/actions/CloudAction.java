package com.dnd.botTable.actions;

import com.dnd.botTable.Action;

public class CloudAction extends Action{

	private static final long serialVersionUID = 1L;

	public static CloudAction create(String name, String text)
	{
		CloudAction action = new CloudAction();
		action.key = 101101001;
		action.name = name;
		action.text = text;
		return action;
	}
	
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
