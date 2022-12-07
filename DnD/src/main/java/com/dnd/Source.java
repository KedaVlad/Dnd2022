package com.dnd;

public interface Source 
{

	final static String I = "\\";
	final static String json = ".json";
	
	final static String source = "C:\\Users\\ALTRON\\git\\Dnd2022\\DnD\\LocalData\\";

	final static String userSource = source + "users\\";

	final static String userSource1 = "file.json";

	final static String raceSource = source + "race\\";

	final static String itemSource = source + "items\\items.txt";

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

	final static String featuresSource = source + "workmanship\\features.json";
	final static String spellsSource = source + "workmanship\\spells.json";
	final static String possessionsSource = source + "workmanship\\possession.json";
	final static String traitsSource = source + "workmanship\\trait.json";

	
	public abstract String source();
}

