package com.dnd.creatingCharacter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.dnd.Dise;
import com.dnd.Source;
import com.dnd.creatingCharacter.bagDnd.*;
import com.dnd.creatingCharacter.classDnd.*;
import com.dnd.creatingCharacter.raceDnd.*;
import com.dnd.creatingCharacter.skills.*;
import com.dnd.factory.CharacterFactory;
import com.dnd.factory.ClassFactory;
import com.dnd.factory.RaceFactory;
import com.dnd.factory.WorkmanshipFactory;


public class CharacterDnd implements Dise,Source,Serializable{

	//CharacterDnd basic information//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String name;
	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass = null;
	private String nature;
	private int speed;



	//Game states////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private int profisiency;
	private int hp;
	private int statsFreePoints = 0;
	private Map<String, Skill> mySkills;
	private Map<String, Spell> mySpells;
	private Map<String, Possession> myPossession;
	private Map<String, Bag> myBags;
	private static final long serialVersionUID = 1L;


	private Map<String, Integer> myStats;

	private static final String[][] natures = {{"Chaotic", "Neutral", "Lawful"},{"Evil","Neutral","Kind"}};

	//Create////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public CharacterDnd(String name) 
	{
		this.name = name;
		myBags = new LinkedHashMap<>();
		mySkills = new LinkedHashMap<>();
		satBag("Bag");
		myStats = new LinkedHashMap<>();


	}

	public void setStats(int str,int dex, int con, int intl,int wis,int cha) 
	{

		myStats.put("Strength", str);
		myStats.put("Dexterity", dex);
		myStats.put("Constitution", con);
		myStats.put("Intelligence", intl);
		myStats.put("Wisdom", wis);
		myStats.put("Charisma", cha);

	}

	//Play//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void lvlUp() 
	{
		myClass.setLvl(myClass.getLvl()+1);
		WorkmanshipFactory.getWorkmanship(ClassFactory.getWorkmanshipClass(myClass));
	}

	//Getter`s & Setter`s///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ClassDnd getClassDnd() 
	{
		return myClass;
	}

	public void setHp(int hp) 
	{
		this.hp = hp;
	}

	public int getLvl() 
	{
		return myClass.getLvl();
	}

	public void setHp() 
	{

		int statFacktor = myStats.get("Constitution");
		int result = myClass.getDiceHits()+statFacktor;
		for(int i=1; i< getLvl(); i++) {
			result += Math.round(Math.random()*(myClass.getDiceHits()-1) + 1);

		}

		this.hp = result;
	}

	public int getHp() 
	{
		return hp;
	}

	public void setProfisiency() 
	{

		int result;

		if(getLvl()>16) {
			result = 4;
		} else if(getLvl()>9) {
			result = 3;
		} else result = 2;

		this.profisiency = result;

	}

	public int getProfisiency() 
	{
		return profisiency;
	}

	public String setNature(int x, int y) 
	{

		return nature = natures[0][y]+" "+natures[1][y];

	}

	public String getNature() 
	{
		return nature;
	}

	public String getName() 
	{
		return name;
	}

	public Bag getBag(String bag) 
	{
		return myBags.get(bag);
	}

	public Map<String, Skill> getMySkills() 
	{
		return mySkills;
	}

	public Map<String, Spell> getMySpells() 
	{
		return mySpells;
	}

	public void satBag(String bagName) 
	{
		if(!myBags.containsKey(bagName)) {
			myBags.put(bagName, new Bag(bagName));
		} else {
			System.out.println("U already got it");
		}
	}

	public RaceDnd getMyRace() 
	{
		return myRace;
	}

	public ClassDnd getMultiClass() 
	{
		return multiClass;
	}

	public void setMultiClass(ClassDnd multiClass) 
	{
		this.multiClass = multiClass;
	}

	public int getSpeed() 
	{
		return speed;
	}

	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}

	public int getStatsFreePoints() 
	{
		return statsFreePoints;
	}

	public void setRaceDnd(RaceDnd raceDnd) 
	{
		this.myRace = raceDnd;
	}

	public void setClassDnd(ClassDnd classDnd) 
	{
		this.myClass = classDnd;
	}

	public void setStatsFreePoints(int statsFreePoints) 
	{
		this.statsFreePoints = statsFreePoints;
	}
	public Map<String, Possession> getMyPossession() {
		return myPossession;
	}



	//Bag///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	class Bag 
	{


		private int id;
		private String bagName;
		private List<Items> insideBag;


		public Bag(String bagName) {
			this.bagName = bagName;
			insideBag = new ArrayList<Items>();
		}




		public void whatInTheBag() {
			if(getInsideBag().size() == 0) {
				System.out.println("Your bag is empty!");
			} else {
				for(int i = 0; i < getInsideBag().size(); i++) {
					System.out.println(getInsideBag().get(i));
				}
			}
		}

		//equals + hashCode()
		public boolean equals(Object obj) {
			if(obj == this) return true;
			if(obj == null || obj.getClass() != this.getClass()) return false;
			Bag characterDnd = (Bag) obj;
			return id == characterDnd.id && (name == characterDnd.bagName ||(name != null && name.equals(characterDnd.bagName)));
		}
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());             
			result = prime * result + id; 
			return result;
		}


		public List<Items> getInsideBag() {
			return insideBag;
		}
	}



}









