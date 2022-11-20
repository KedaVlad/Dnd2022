package com.dnd.dndTable.creatingDndObject.classDnd;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Rogue extends ClassDnd {

	private static final long serialVersionUID = -114317420459911172L;
	private String myArchetypeClass = "Assasin";
	
	

	public Rogue( int lvl, String archetype) {
		super(lvl);
		this.myArchetypeClass = archetype;
	}
	public Rogue()
	{
		
	}

	public String getMyArchetypeClass() {
		return myArchetypeClass;
	}
	@JsonIgnore
	public int getDiceHits() {
		return 8;
	}
	
	public String toString() {
		return "Rogue";
	}
	@Override
	public List<String> getPermanentBuffs() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getFirstHp() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
