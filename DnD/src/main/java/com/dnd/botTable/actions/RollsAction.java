package com.dnd.botTable.actions;

import com.dnd.botTable.Action;

public class RollsAction extends BotAction 
{
	private static final long serialVersionUID = 1L;
	
	public RollsAction()
	{
		this.mainAct = true;
		this.name = "SomeRoll";
		this.text = "Choose dice which you want roll";
	}
	
	@Override
	public Action continueAction(String key) 
	{
		this.setAnswer(key);
		return this;
	}

	
	@Override
	protected String[][] buildButtons() 
	{
		return new String[][]{{"D4","D6","D8","D10","D12","D20","D100"}};
	}

	
	@Override
	public boolean hasButtons() {
		
		return true;
	}
}
