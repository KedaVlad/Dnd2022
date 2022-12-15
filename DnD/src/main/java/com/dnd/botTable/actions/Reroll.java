package com.dnd.botTable.actions;

import java.util.List;

public class Reroll extends AttackAction 
{

	private static final long serialVersionUID = 1L;

	public static Reroll create(AttackAction action)
	{
		Reroll answer = new Reroll();
		answer.name = "REROLL";
		answer.key = action.getKey();
		answer.mainAct = true;
		answer.mediator = false;
		return answer;
	}
}
