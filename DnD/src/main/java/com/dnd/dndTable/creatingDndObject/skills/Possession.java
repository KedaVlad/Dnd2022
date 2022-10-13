package com.dnd.dndTable.creatingDndObject.skills;

import java.io.Serializable;

public class Possession implements Serializable {

	private static final long serialVersionUID = 863271851968078819L;

	private int id;
	private String name;
	
	public Possession(String name) 
	{
	this.name = name;
	}
	
	public boolean equals(Object obj) 
{
		if(obj == this) return true;
		if(obj == null || obj.getClass() != this.getClass()) return false;
		Possession characterDnd = (Possession) obj;
		return id == characterDnd.id && (getName() == characterDnd.getName() ||(getName() != null && getName().equals(characterDnd.getName())));
	}

	public int hashCode() 
{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());             
		result = prime * result + id; 
		return result;

}

	public String getName() {
		return name;
	}
}
