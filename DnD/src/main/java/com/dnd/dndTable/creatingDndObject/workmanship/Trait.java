package com.dnd.dndTable.creatingDndObject.workmanship;

import java.io.Serializable;

import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;

public class Trait extends Feature implements Serializable {

	private static final long serialVersionUID = -8115502742156146242L;
	
	private int id;
	private String name;
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null || obj.getClass() != this.getClass()) return false;
		Trait characterDnd = (Trait) obj;
		return id == characterDnd.id && (name == characterDnd.name ||(name != null && name.equals(characterDnd.name)));
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());             
		result = prime * result + id; 
		return result;

}
}
