package com.dnd.creatingCharacter.spells;

import java.util.HashMap;
import java.util.Map;


public abstract class Spells {
	
	
	private static Spells[] allSpellsArr = {new Prestidigitation()};
	private static Map<String, Spells> allSpells;
		
	
	private int lvlSpell;
	private static String cast;
	private static String superCast;
	private static String name;
	private static String description;
	private static String applicationTime;
	private static int distanse;
	private static int duration;
	
	
	
	static {
		allSpells = new HashMap<>();
		for(int i = 0; i < allSpellsArr.length; i++) {
			getAllSpells().put(allSpellsArr[i].getName(), allSpellsArr[i]);
		}
	}
	 


	public Spells() {
		

	}
	
	
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

	public static String getCast() {
		return cast;
	}

	public static String getSuperCast() {
		return superCast;
	}


	public static Map<String, Spells> getAllSpells() {
		return allSpells;
	}

}
class Biba extends Spells{
	public String toString() {
		return "Its biba";
	}
}
