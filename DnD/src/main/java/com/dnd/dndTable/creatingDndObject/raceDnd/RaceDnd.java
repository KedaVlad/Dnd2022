package com.dnd.dndTable.creatingDndObject.raceDnd;


import java.io.File;
import java.io.Serializable;
import java.util.Map;

import com.dnd.Log;
import com.dnd.Log.Place;
import com.dnd.dndTable.ObjectDnd;

public class RaceDnd implements Serializable, ObjectDnd{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7603608846317166137L;
	
	private int speed;
	private String raceName;
	private String subRace;
	private File raceMainFile;
	
	
	//Stats baff
	private int freePoint;



	//Standard creating 
	

	public RaceDnd(String raceName, String subRace) {
		this.raceName = raceName;
		this.subRace = subRace;
		
		Log.add(subRace + " CREATED", Place.RACE);
		
	}
	
	
	public void raceBuff(Map<String,Integer> stats) {
	

}


	public String getRaceName() {
		return raceName;
	}


	public String getSubRace() {
		return subRace;
	}


	public File getRaceMainFile() {
		return raceMainFile;
	}


	public void setRaceMainFile(File raceMainFile) {
		this.raceMainFile = raceMainFile;
	}

}

