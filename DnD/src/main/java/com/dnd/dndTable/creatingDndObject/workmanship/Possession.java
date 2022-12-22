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

	private static final long serialVersionUID = 863271851968078819L;
	
	private String name;
	private ProfComand inerComand;
	
	public Possession() {}
	
	public Possession(String name, long key) 
	{
	this.name = name;
	this.inerComand = ProfComand.create(key, name);
	}
	
	public Possession competence()
	{
		this.inerComand.competence();
		return this;
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
	
	public String getName() {
		return name;
	}

	public InerComand getInerComand() {
		return inerComand;
	}

	public void setInerComand(ProfComand inerComand) {
		this.inerComand = inerComand;
	}
	
	public static void main(String[] args) throws JsonProcessingException 
	{
		
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return possession;
	}
}
