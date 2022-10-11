package com.dnd.dndTable.creatingDndObject.skills;

import java.io.Serializable;

public class Possession implements Workmanship, Serializable {

	private static final long serialVersionUID = 863271851968078819L;

	public Possession(String name) {
	
	}
	private int id;
	private String name;
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null || obj.getClass() != this.getClass()) return false;
		Possession characterDnd = (Possession) obj;
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
