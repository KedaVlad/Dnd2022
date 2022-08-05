package com.dnd.creatingCharacter.raceDnd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.dnd.Dise;
import com.dnd.creatingCharacter.spells.*;

abstract public class RaceDnd implements Serializable, Dise{

	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;
	private int age;
	private int weight;
	private int growth;
	protected int speed = 30;
	private String[] skillsRace;


	//Standard creating 
	RaceDnd(int age, int weight, int growth) {
		this.age = age;
		this.weight = weight;
		this.growth = growth;
	}
	

	public String[] getSkillsRace() {
		return skillsRace;
	}
	
	public int getStr(int str) {
		return str;
	}

	public int getAgl(int agl) {
		return agl;
	}
	public int getDex(int dex) {
		return dex;
	}
	public int getIntl(int intl) {
		return intl;
	}
	public int getWis(int wis) {
		return wis;
	}
	public int getCha(int cha) {
		return cha;
	}
	public int getAge() {
		return age;
	}
	public int getWeight() {
		return weight;
	}
	public int getGrowth() {
		return growth;
	}
	public int getSpeed() {
		return speed;
	}


}

//Random creating 
	/*RaceDnd() {
		this.age = (int) Math.round(Math.random()*90);
		this.weight = (int) Math.round(Math.random()*10);
		this.growth = (int) Math.round(Math.random()*10);
		skillsRace = new HashMap<>();
	}

	public static RaceDnd randomRace() {
		RaceDnd[] allRace = {new Gnome(),new HalflingLightfoot(20,5,5)};
		int i = (int) Math.round(Math.random() * allRace.length) -1;
		if(i < 0) return allRace[0];
		return allRace[i];
	}*/
