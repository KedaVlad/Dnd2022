package com.dnd.creatingCharacter.classDnd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Rogue extends ClassDnd{

	private int hits = 8;
	private int DiceHits = d8;

	private String myArchetypeClass;
	private File myClassMainFile;

	{
		setMainFile();
	}
	public Rogue( int lvl, String archetype) {
		super(lvl,archetype);
		
	
	}

	public String getMyArchetypeClass() {
		return myArchetypeClass;
	}
	
	public String toString() {
		return "Rogue";
	}

	@Override
	public void setMainFile() {
		this.myClassMainFile = new File(rogueSource + myArchetypeClass + ".txt");
		
	}
	

}
