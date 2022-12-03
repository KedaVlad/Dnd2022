package com.dnd.dndTable.creatingDndObject.workmanship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dnd.dndTable.creatingDndObject.workmanship.magicEffects.Effect;


public class Spell implements Serializable {
	
	
	private static final long serialVersionUID = -7876613939972469105L;
	
	private int lvlSpell;
	private Effect cast;
	private String name;
	private String description;
	private String applicationTime;
	private int distanse;
	private int duration;

	private List<String> classFor;
	
	public Spell(String name) {
		this.name = name;
		classFor = new ArrayList<>();

	}
	private int id;
	
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null || obj.getClass() != this.getClass()) return false;
		Spell characterDnd = (Spell) obj;
		return id == characterDnd.id && (name == characterDnd.name ||(name != null && name.equals(characterDnd.name)));
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());             
		result = prime * result + id; 
		return result;

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

	public Effect getCast() {
		return cast;
	}


	public void setCast(Effect cast) {
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

