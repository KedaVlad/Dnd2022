package com.dnd.creatingCharacter.spells;


public class SomeSpellLvlOne extends Spells {

	private final int lvlSpell = 1;
	private final String cast = "some staff";
	private final String superCast = " + super staff";
	private final String name = "Spell1";
	private final String description;
	private final String applicationTime = "1 min";
	private final int distanse = 50;
	private final int duration = 20;
	
	public SomeSpellLvlOne() {
		this.description = "";
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

}
