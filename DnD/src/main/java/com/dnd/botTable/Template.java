package com.dnd.botTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dnd.dndTable.rolls.actions.HeroAction;

public class Template {

	private String name;
	private String text;
	private boolean mainAct;
	private List<String> buttons;
	private HeroAction action;

	public Template(HeroAction action, boolean mainAct)
	{
		this.action = action;
		this.mainAct = mainAct;
		this.name = action.getName();
		this.text = action.getText();
		this.buttons = new ArrayList<>();
		if(!action.isLastStep())
		{
			for(int i = 0; i < action.getNextStep().size(); i++)
			{
				getButtons().add(action.getNextStep().get(i).getName());
			}
		}

	}

	public String getText() 
	{
		return text;
	}

	public void setText(String message) 
	{
		this.text = message;
	}

	public boolean isMainAct() 
	{
		return mainAct;
	}

	public void setMainAct(boolean mainAct) 
	{
		this.mainAct = mainAct;
	}

	public boolean hasButtons() 
	{
		return buttons !=null && !buttons.isEmpty();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HeroAction getAction() {
		return action;
	}

	public void setAction(HeroAction action) {
		this.action = action;
	}

	public List<String> getButtons() {
		return buttons;
	}

}
