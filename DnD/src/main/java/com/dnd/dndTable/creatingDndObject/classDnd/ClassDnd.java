package com.dnd.dndTable.creatingDndObject.classDnd;

import java.io.File;
import java.io.Serializable;

import com.dnd.Log;
import com.dnd.Log.Place;
import com.dnd.dndTable.ObjectDnd;




public abstract class ClassDnd implements Serializable,ObjectDnd{

	private static final long serialVersionUID = 3219669745475635442L;
	
	//private final int firstLvlhits;
	private String myArchetypeClass;
	
	private int lvl;
	private File myClassMainFile;
	//Standard creating 
	public ClassDnd(int lvl) {		
	this.lvl = lvl;
	}
	

	public int getDiceHits() {
		return 0;
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
		Log.add("setMainFile", Place.CLASS);
	}


	public File getMyClassMainFile() {
		return myClassMainFile;
	}

	

}


