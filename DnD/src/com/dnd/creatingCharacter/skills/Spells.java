package com.dnd.creatingCharacter.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Spells implements SkillsSource {
	
	private int lvlSpell;
	private String cast;
	private String name;
	private String description;
	private String applicationTime;
	private int distanse;
	private int duration;

	private List<String> classFor;
	
	public Spells(String name) {
		this.name = name;
		classFor = new ArrayList<>();

	}
	
	
	public String castSpell(Spells spell, int spellSlot) {
		String result = getCast(); 
		if(spell.getLvlSpell() < spellSlot) { return result;
		} else {
		for(int i = 0; i < spellSlot; i++) {
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

	public String getCast() {
		return cast;
	}


	public void setCast(String cast) {
		this.cast = cast;
	}


	public List<String> getClassFor() {
		return classFor;
	}


	public void setClassFor(String ... strings) {
		for(String string: strings) {
			this.classFor.add(string);
		}
		
	}

	



}

