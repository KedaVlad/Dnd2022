package com.dnd.botTable;

import java.util.ArrayList;

import com.dnd.botTable.actions.BaseAction;

public class ActBuilder
{
	private String name;
	private String text;
	private String returnTarget;
	private String returnCall;
	private BaseAction action;

	ActBuilder(){}

	public ActBuilder name(String name)
	{
		this.name = name;
		return this;
	}

	public ActBuilder text(String text)
	{
		this.text = text;
		return this;
	}

	public ActBuilder returnTo(String actName)
	{
		this.returnTarget = actName;
		return this;
	}

	public ActBuilder returnTo(String actName, String answer)
	{
		this.returnTarget = actName;
		this.returnCall = answer;
		return this;
	}

	public ActBuilder action(BaseAction action)
	{
		this.action = action;
		return this;
	}
	

	public Act build()
	{
		Act answer = new Act();
		answer.name = this.name;
		answer.text = this.text;
		answer.action = this.action;
		answer.actCircle = new ArrayList<>();
		answer.returnTarget = this.returnTarget;
		answer.returnCall = this.returnCall;
		return answer;
	}

}

