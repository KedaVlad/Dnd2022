package com.dnd.creatingCharacter.skills;

public class Skill implements SkillsSource{
	
	private String name;
	private String description;
	
	private String treats;
	private int statsPointsBuff;
	

	public Skill(String name, String description) {
		
		this.name = name;
		this.description = description;
		
	}


	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}



	public String getTreats() {
		return treats;
	}



	public void setTreats(String treats) {
		this.treats = treats;
	}



	public int getStatsPointsBuff() {
		return statsPointsBuff;
	}



	public void setStatsPointsBuff(int statsPointsBuff) {
		this.statsPointsBuff = statsPointsBuff;
	}
	

}
