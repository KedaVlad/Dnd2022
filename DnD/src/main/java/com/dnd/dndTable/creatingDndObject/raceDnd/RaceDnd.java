package com.dnd.dndTable.creatingDndObject.raceDnd;


import java.io.Serializable;
import java.util.Map;

import com.dnd.dndTable.ObjectDnd;

public class RaceDnd implements Serializable, ObjectDnd{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7603608846317166137L;
	
	private int speed;
	private String raceName;
	private String subRace;
	
	
	//Stats baff
	private int freePoint;



	//Standard creating 
	

	public RaceDnd(String raceName, String subRace) {
		this.raceName = raceName;
		this.subRace = subRace;
		
	}
	
	
	public void raceBuff(Map<String,Integer> stats) {
	

}


	public String getRaceName() {
		return raceName;
	}


	public String getSubRace() {
		return subRace;
	}

}

