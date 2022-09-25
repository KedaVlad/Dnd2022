package com.dnd.dndTable.creatingDndObject.classDnd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Rogue extends ClassDnd {

	private int hits = 8;


	private String myArchetypeClass;
	

	public Rogue( int lvl, String archetype) {
		super(lvl,archetype);
		
	
	}

	public String getMyArchetypeClass() {
		return myArchetypeClass;
	}
	
	public String toString() {
		return "Rogue";
	}
	
}
