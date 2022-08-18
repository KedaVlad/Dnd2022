package com.dnd.creatingCharacter.classDnd;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dnd.Dise;


public abstract class ClassDnd implements Dise{



	private int hits;
	private int DiceHits;
	private String myArchetypeClass;
	private int lvl;
	private File[] classArchetype;
	
	{
		setClassArchetype();
	}
	//Standard creating 
	public ClassDnd(int lvl){		
	this.lvl = lvl;
	
	System.out.println("Syka 1w");


	}
	
	public void showArchetype() {
		for(int i = 0; i < getClassArchetype().length; i ++) {
			System.out.println(getClassArchetype()[i].getName());
		}
	}
	

	public int getDiceHits() {
		return DiceHits;
	}

	public int getHits() {
		return hits;
	}

	public String getMyArchetypeClass() {
		return myArchetypeClass;
	}




	public int getLvl() {
		return lvl;
	}


	public abstract void setClassArchetype();

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public File[] getClassArchetype() {
		return classArchetype;
	}

}


