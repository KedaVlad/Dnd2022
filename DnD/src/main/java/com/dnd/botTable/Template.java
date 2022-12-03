package com.dnd.botTable;

import java.util.List;
import java.util.Map;

import com.dnd.dndTable.rolls.actions.HeroAction;

public class Template {

	private String name;
	private String text;
	private boolean mainAct;
	private List<String> buttons;
	private HeroAction action;
	
	public Template( HeroAction action, boolean mainAct)
	{
		this.name = action.getName();
		this.text = action.getText();
		this.buttons =
		this.mainAct = mainAct;
	}
	
	public Template(String name, String message, boolean mainAct)
	{
		this.name = name;
		this.text = message;
		this.mainAct = mainAct;
	}
	
	public Template(String name, String message, boolean mainAct, Map<String, String> buttons)
	{
		this.name = name;
		this.text = message;
		this.mainAct = mainAct;
		this.buttons = buttons;
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
		return buttons!=null && !buttons.isEmpty();
	}
	
	
	public Map<String, String> getButtons() 
	{
		return buttons;
	}
	
	public void setButtons(Map<String, String> buttons) 
	{
		this.buttons = buttons;
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
	
}
