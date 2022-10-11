package com.dnd.dndTable.creatingDndObject.classDnd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;
import com.dnd.Log.Place;

public class Rogue extends ClassDnd {

	/**
	 * 
	 */
	private static final long serialVersionUID = -114317420459911172L;
	private String myArchetypeClass = "Assasin";
	
	

	public Rogue( int lvl, String archetype) {
		super(lvl);
		this.myArchetypeClass = archetype;
		Log.add("Rogue " + archetype + "CREATED", Place.CLASS);	
	
	}

	public String getMyArchetypeClass() {
		return myArchetypeClass;
	}
	
	public int getDiceHits() {
		return 8;
	}
	
	public String toString() {
		return "Rogue";
	}
	
}
