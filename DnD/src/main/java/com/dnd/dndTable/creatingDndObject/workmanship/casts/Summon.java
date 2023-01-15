package com.dnd.dndTable.creatingDndObject.workmanship.casts;

public class Summon extends Cast
{

	private String text;
	public static Summon create(String text)
	{
		Summon answer = new Summon();
		answer.text = text;
		return answer;
	}

	public String getText() {
		return text;
	}
	
}
