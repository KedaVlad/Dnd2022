package com.dnd.creatingCharacter.classDnd;

import com.dnd.Source;

public interface ClassSource extends Source{
	
	
	final static String classSource = source + "classes\\";
	
	
	final static String artificerSource = classSource + "Artificer\\";
	final static String barbarianSource = classSource + "Barbarian\\";
	final static String bardSource = classSource + "Bard\\";
	final static String bloodHunterSource = classSource + "BloodHunter\\";
	final static String clericSource = classSource + "Cleric\\";
	final static String druidSource = classSource + "Druid\\";
	final static String fighterSource = classSource + "Fighter\\";
	final static String monkSource = classSource + "Monk\\";
	final static String rogueSource = classSource + "Rogue\\";
	final static String warlockSource = classSource + "Warlock\\";
	final static String wizardSource = classSource + "Wizard\\";
	final static String homeBrewSource = classSource + "HomeBrew\\";
	
	public void setMainFile();

}
