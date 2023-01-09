package com.dnd.dndTable.creatingDndObject.workmanship;

import java.io.Serializable;

import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.factory.inerComands.InerComand;
import com.dnd.dndTable.factory.inerComands.ProfComand;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
@JsonTypeName("POSSESSION")
public class Possession implements Serializable, ObjectDnd
{
	public enum Proficiency
	{
		BASE, HALF, COMPETENSE
	}
	
	private static final long serialVersionUID = 863271851968078819L;

	private Proficiency prof;
	private String name;
	public Possession() {}
	
	public Possession(String name) 
	{
	this.name = name;
	this.prof = Proficiency.BASE;
	}
	
	public Possession(String name, Proficiency prof)
	{
		this.name = name;
		this.prof = prof;
	}
	
	public String toString()
	{
		if(prof.equals(Proficiency.HALF))
		{
			return name + " (half proficiency)";
		}
		else if(prof.equals(Proficiency.COMPETENSE))
		{
			return name + " (competense)";
		}
		else
		{
			return name;
		}
	}
	
	
/*	public boolean equals(Object obj) 
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
*/

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return POSSESSION;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Proficiency getProf() {
		return prof;
	}

	public void setProf(Proficiency prof) {
		this.prof = prof;
	}
}
