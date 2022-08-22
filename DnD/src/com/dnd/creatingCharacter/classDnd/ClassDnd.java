package com.dnd.creatingCharacter.classDnd;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dnd.Dise;


public abstract class ClassDnd implements Dise, ClassSource{



	//private final int firstLvlhits;
	private int DiceHits;
	private String myArchetypeClass;
	
	private int lvl;
	private File myClassMainFile;
	{
		setMainFile();
	}
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

	public void setMainFile() {
		this.myClassMainFile = new File(source);
	}


	public File getMyClassMainFile() {
		return myClassMainFile;
	}

	

}


