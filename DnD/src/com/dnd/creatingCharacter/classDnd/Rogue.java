package com.dnd.creatingCharacter.classDnd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Rogue extends ClassDnd{

	private int hits = 8;
	private int DiceHits = d8;
	
	private File[] classArchetype;
	private String myArchetypeClass;


	{
		setClassArchetype();
	}
	public Rogue( int lvl) {
		super(lvl);
		this.myArchetypeClass = classArchetype[1].getName();
		System.out.println("Syka 11");
	}

	public String getMyArchetypeClass() {
		return myArchetypeClass;
	}
	
	public String toString() {
		return "Rogue";
	}

	public void setClassArchetype() {
		File dir1 = new File("C:\\Users\\ALTRON\\Desktop\\dnd\\classes\\Rogue"); 
		
		this.classArchetype = dir1.listFiles();
	}
	

}
