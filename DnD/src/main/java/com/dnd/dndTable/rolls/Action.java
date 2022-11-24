package com.dnd.dndTable.rolls;

import java.util.List;

public class Action {
	String name;
	Article attack;
	Article hit;
	List<String> spesials;
	
	Action(String name, Article attack, Article hit, List<String> spesials)
	{
		this.name = name;
		this.attack = attack;
		this.hit = hit;
		this.spesials = spesials;
	}
	
	Action(String name, Article attack, Article hit)
	{
		this.name = name;
		this.attack = attack;
		this.hit = hit;
	}
	
}
