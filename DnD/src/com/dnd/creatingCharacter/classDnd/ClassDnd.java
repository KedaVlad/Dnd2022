package com.dnd.creatingCharacter.classDnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dnd.Dise;
import com.dnd.creatingCharacter.spells.*;

public class ClassDnd implements Serializable,Dise{


	private static final long serialVersionUID = 13L;
	private int hits;
	private int DiceHits;
	private int lvl;
	private List<Lvl> myLvl = new ArrayList<>();
	private Map<String, Spells> skillsClass;


	private final Lvl[] allLvl = {new Lvl1(), new Lvl2(),new Lvl3(),new Lvl4(), new Lvl5(),new Lvl6(),new Lvl7(), new Lvl8(),new Lvl9(),new Lvl10(), new Lvl11(),new Lvl12(),new Lvl13(), new Lvl14(),new Lvl15(),new Lvl16(), new Lvl17(),new Lvl18(),new Lvl19(), new Lvl20()};




	//Standard creating 
	public ClassDnd(int lvl){
		if(lvl >20|| lvl <1) {

			this.lvl=d20;
			skillsClass = new HashMap<>();
			System.out.println("Your lvl can`t be aboe lvl 20 or less then lvl 1. So i give you lvl " + this.lvl);
			setStartLvl();

		} else {
			this.lvl=lvl;
			setStartLvl();}

	}
	//Random creating
	public ClassDnd() {
		this.lvl = d20;
		setStartLvl();
	}



	public void setStartLvl() {
		for(int i = 0; i < getLvl(); i++) {
			myLvl.add( allLvl[i]);
			skillsClass.put(allLvl[i].getSkillsLvl().getName(), allLvl[i].getSkillsLvl());
		}
	}

	public Map<String, Spells> getSkillsClass(){
		return skillsClass;
	}
	

	public int getDiceHits() {
		return DiceHits;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		if(lvl >20) {
			System.out.println("Your lvl can`t be aboe lvl 20");
		}
		this.lvl = lvl;
	}
	public int getHits() {
		return hits;
	}




	//For random creating
	public static ClassDnd randomClass() {
		ClassDnd[] allClass = {new Barbarian(),new Fighter()};
		int i = (int) Math.round(Math.random() * allClass.length) - 1;
		if(i < 0) return allClass[0];

		return allClass[i];
	}






	





	public class Lvl{

		public Spells getSkillsLvl() {
			return null;
		}
	}	

	public class Lvl1 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}
	}

	public class Lvl2 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}

	}
	public class Lvl3 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}	
	}
	public class Lvl4 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}	
	}
	public class Lvl5 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}
	}
	public class Lvl6 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}
	}
	public class Lvl7 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}	
	}
	public class Lvl8 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}	
	}
	public class Lvl9 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}	
	}
	public class Lvl10 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}
	}
	public class Lvl11 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}		
	}
	public class Lvl12 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}		
	}
	public class Lvl13 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}		
	}

	public class Lvl14 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}		
	}
	public class Lvl15 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}		
	}
	public class Lvl16 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}		
	}
	public class Lvl17 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}		
	}
	public class Lvl18 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}	
	}
	public class Lvl19 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}		
	}
	public class Lvl20 extends Lvl{
		public Spells getSkillsLvl() {
			return null;
		}		
	}



}


