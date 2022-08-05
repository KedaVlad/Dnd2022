package com.dnd.creatingCharacter.classDnd;

import java.io.Serializable;
import com.dnd.Dise;


public class ClassDnd implements Serializable,Dise{


	private static final long serialVersionUID = 13L;
	private int hits;
	private int DiceHits;
	private String myArchetypeClass;
	private String[] allArchetypeClass;
	private String[][] lvlSkills;



	//Standard creating 
	public ClassDnd(int subClass){			

	}
	
	public void setSubClassSkills() {
		
	}
	

	public int getDiceHits() {
		return DiceHits;
	}

	public int getHits() {
		return hits;
	}

	public String getSubClass() {
		return myArchetypeClass;
	}

	public String[][] getLvlSkills() {
		return lvlSkills;
	}







	



	// Lvl[] allLvl = {new Lvl1(),new Lvl2(),new Lvl3(),new Lvl4(),new Lvl5(),new Lvl6(),new Lvl7(),new Lvl8(),new Lvl9(),new Lvl10(),
		//		 new Lvl11(),new Lvl12(),new Lvl13(),new Lvl14(),new Lvl15(),new Lvl16(),new Lvl17(),new Lvl18(),new Lvl19(),new Lvl20()};


	/*public class Lvl{

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
	}*/



}


