package com.dnd.dndTable.creatingDndObject.workmanship;

import java.io.Serializable;

import com.dnd.dndTable.factory.InerComand;

public class Possession implements Serializable {

	private static final long serialVersionUID = 863271851968078819L;

	private int id;
	private String name;
	private InerComand inerComand;
	
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

	public InerComand getInerComand() {
		return inerComand;
	}

	public void setInerComand(InerComand inerComand) {
		this.inerComand = inerComand;
	}
}
