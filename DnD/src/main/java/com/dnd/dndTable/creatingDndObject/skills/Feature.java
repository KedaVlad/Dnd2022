package com.dnd.dndTable.creatingDndObject.skills;

import java.io.Serializable;

import com.dnd.Log;
import com.dnd.Log.Place;

public class Feature implements Serializable{
	
	private static final long serialVersionUID = 5053270361827778941L;
	
	
	private String description;
	
	private String treats;
	private int statsPointsBuff;
	

	public Feature(String name) {
		
		this.name = name;
	}
	
	private int id;
	private String name;
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null || obj.getClass() != this.getClass()) return false;
		Feature characterDnd = (Feature) obj;
		return id == characterDnd.id && (name == characterDnd.name ||(name != null && name.equals(characterDnd.name)));
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());             
		result = prime * result + id; 
		return result;
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
