package com.dnd.creatingCharacter.raceDnd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.dnd.Dise;
import com.dnd.creatingCharacter.spells.*;

abstract public class RaceDnd implements RaceSource, Dise{

	
	private int age;
	private int weight;
	private int growth;
	private int speed;
	private String race;
	
	
	//Stats baff
	private int freePoint;



	//Standard creating 
	RaceDnd(String race, int age, int weight, int growth) {
		this.age = age;
		this.weight = weight;
		this.growth = growth;
	
	}
	RaceDnd(String race) {
		
	}
	
	
	public void raceBuff(Map<String,Integer> stats) {
	
	
	

	
	
	
	


}

