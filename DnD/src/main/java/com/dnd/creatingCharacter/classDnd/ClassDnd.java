package com.dnd.creatingCharacter.classDnd;

import java.io.File;
import java.io.Serializable;

import com.dnd.Dise;
import com.dnd.Source;


public abstract class ClassDnd implements Dise, Source,Serializable{

	private static final long serialVersionUID = 3219669745475635442L;
	
	//private final int firstLvlhits;
	private int DiceHits;
	private String myArchetypeClass;
	
	private int lvl;
	private File myClassMainFile;
	//Standard creating 
	public ClassDnd(int lvl, String arhcetype) {		
	this.lvl = lvl;
	this.myArchetypeClass = arhcetype;
	}
	

	public int getDiceHits() {
		return DiceHits;
	}


	public String getMyArchetypeClass() {
		return myArchetypeClass;
	}




	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public void setMainFile(File file) {
		this.myClassMainFile = file;
	}


	public File getMyClassMainFile() {
		return myClassMainFile;
	}

	

}


