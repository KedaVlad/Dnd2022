package com.dnd.botTable.actions;

import com.dnd.dndTable.creatingDndObject.ObjectDnd;
import com.dnd.dndTable.factory.inerComands.InerComand;

public class ActionBuilder extends BaseActionBuilder<ActionBuilder>
{
	private String[][] buttons;
	private ObjectDnd objectDnd;
	private InerComand comand;

	ActionBuilder(){}

	public ActionBuilder buttons(String[][] buttons)
	{
		this.buttons = buttons;
		return this;
	}

	public ActionBuilder objectDnd(ObjectDnd objectDnd)
	{
		this.objectDnd = objectDnd;
		return this;
	}

	public ActionBuilder comand(InerComand comand)
	{
		this.comand = comand;
		return this;
	}
	
	public Action build()
	{
		Action action = new Action();
		action.name = this.name;
		action.replyButtons = this.replyButtons;
		action.mediator = this.mediator;
		if(this.cloud)
		{
			action.cloud = this.cloud;
			if(buttons == null)
			{
				this.buttons = new String[][] {{"ELIMINATION"}};
			}
			else
			{
				String[][] newButtons = new String[this.buttons.length + 1][];
				for(int i = 0; i < this.buttons.length; i++)
				{
					newButtons[i] = this.buttons[i];
				}
				newButtons[this.buttons.length] = new String[] {"ELIMINATION"};
				this.buttons = newButtons;
			}
		}
		action.buttons = this.buttons;
		action.objectDnd = this.objectDnd;
		action.location = this.location;
		action.key = this.key;
		action.comand = this.comand;
		return action;
	}

	@Override
	protected ActionBuilder self() 
	{
		return this;
	}

}
