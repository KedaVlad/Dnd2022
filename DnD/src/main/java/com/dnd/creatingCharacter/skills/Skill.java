package com.dnd.creatingCharacter.skills;

import java.io.Serializable;

public class Skill implements Workmanship, Serializable{
	
	private static final long serialVersionUID = 5053270361827778941L;
	
	private String name;
	private String description;
	
	private String treats;
	private int statsPointsBuff;
	

	public Skill(String name) {
		
		this.name = name;
		
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
	public String toString() {
		return name + " - "+ description;
	}
	

}
