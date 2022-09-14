package com.dnd.creatingCharacter.raceDnd;


import java.io.Serializable;
import java.util.Map;

import com.dnd.Dise;

public class RaceDnd implements Dise,Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7603608846317166137L;
	
	private int speed;
	private String raceName;
	
	
	//Stats baff
	private int freePoint;



	//Standard creating 
	

	public RaceDnd(String race) {
		this.raceName = race;
		
	}
	
	
	public void raceBuff(Map<String,Integer> stats) {
	
	
	

	
	
	
	


}


	public String getRaceName() {
		return raceName;
	}

}

