package com.dnd.creatingCharacter.spells;

public class Prestidigitation extends Spells{
	
	private static int lvlSpell = 0;
	private final String name = "Prestidigitation";
	private final String applicationTime = "1 action";
	private final int distanse = 10;
	private final int duration = 60;
	
	public Prestidigitation() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public String getApplicationTime() {
		return applicationTime;
	}
	public int getDistanse() {
		return distanse;
	}
	public int getDuration() {
		return duration;
	}



}