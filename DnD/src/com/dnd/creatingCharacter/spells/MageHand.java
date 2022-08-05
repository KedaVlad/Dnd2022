package com.dnd.creatingCharacter.spells;

public class MageHand extends Spells{

	private int lvlSpell = 0;
	private static String superCast = "";
	private static String name = "Mage hand";
	private static String description;
	private static String applicationTime = "1 action";
	private static int distanse = 30;
	private static int duration = 1;
	
	public String castSpell(Spells spell, int spellSlot) {
		String result = getCast(); 
		if(spell.getLvlSpell() < spellSlot) { return result;
		} else {
		for(int i = 0; i < spellSlot; i++) {
		result += getSuperCast();
		} 
		return result;
		}
	}
    public String castSpell(Spells spell) {
	return getCast();
	}

	public String getDescription() {
		return description;
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

	public String toString() {
		return getName() + ": application time("+getApplicationTime()+"), distance("+getDistanse()+"), duration ("+getDuration()+").";
	}

	public int getLvlSpell() {
		return lvlSpell;
	}


	public static String getSuperCast() {
		return superCast;
	}


	public MageHand() {
		// TODO Auto-generated constructor stub
	}

}
