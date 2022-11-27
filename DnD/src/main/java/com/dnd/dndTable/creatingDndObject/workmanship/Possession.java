package com.dnd.dndTable.creatingDndObject.workmanship;

import java.io.Serializable;

import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponType;
import com.dnd.dndTable.factory.InerComand;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.factory.Script;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class Possession implements Serializable, Script {

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
	

	public static void main(String[] args) throws JsonProcessingException 
	{
		Possession target = new Possession("SR Dexterity");
		InerComand comand = new InerComand(false, true, );
		comand.getComand().get(0).add(WeaponType.SHORTSWORD);
		target.setInerComand(comand);
		
		
		JsonNode aa = Json.toJson(target);
		
		System.out.println( Json.stingify(aa));
		
	
	}
}
