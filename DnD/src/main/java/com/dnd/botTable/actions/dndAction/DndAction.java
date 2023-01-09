package com.dnd.botTable.actions.dndAction;

import com.dnd.botTable.Action;
import com.dnd.botTable.actions.BotAction;

public abstract class DndAction extends Action
{
	private static final long serialVersionUID = 1L;
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public Action rebuild()
	{
		BotAction answer = new BotAction();
		answer.setText(this.text);
		answer.setName(this.name);
		answer.setMainAct(true);
		return answer;
	}
}
