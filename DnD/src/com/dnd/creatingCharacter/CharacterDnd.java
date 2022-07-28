package com.dnd.creatingCharacter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dnd.Dise;
import com.dnd.creatingCharacter.bagDnd.*;
import com.dnd.creatingCharacter.classDnd.*;
import com.dnd.creatingCharacter.raceDnd.*;
import com.dnd.creatingCharacter.spells.*;


public class CharacterDnd implements Serializable,Dise{

	//Serialize
	private static final long serialVersionUID = 11L;

	//CharacterDnd basic information
	private int id = 0;
	private String name;
	private RaceDnd raceDnd;
	private ClassDnd classDnd;
	private String nature;
	private Bag bag;

	//Game states
	private int profisiency;
	private int hp;
	private int[] statsValues = new int[6];
	private String[] statsName = {"Strength ","Dexterity ","Constitution ","Intelligense ","Wisdom ","Charisma "};
	private String[][] natures = {{"Chaotic", "Neutral", "Lawful"},{"Evil","Neutral","Kind"}};
	
	private Map<String, Spells> mySpells;
	
	public void showSkills() {
		for(Map.Entry<String, Spells> entry: mySpells.entrySet()) {
			System.out.println(entry.toString());
		}
	}



	// Full creating
	public CharacterDnd(String name, RaceDnd raceDnd, ClassDnd classDnd) {

		this.name = name;
		this.raceDnd = raceDnd;
		this.classDnd = classDnd;
		bag = new Bag();
		id++;
		setProfisiency();
		mySpells = new HashMap<>();
		mySpells.putAll(raceDnd.getSkillsRace());
		mySpells.putAll(classDnd.getSkillsClass());
		mySpells = new HashMap<>();
		
		setSomeSpell("Prestidigitation");
	}
	
	public void setSomeSpell(String spellName) {
		mySpells.put(Spells.getAllSpells().get(spellName).getName(),Spells.getAllSpells().get(spellName));
	}
	public void castSomeSpell(String nameSpell) {
		System.out.println(mySpells.get(nameSpell));
	}
	// Class random creating
	public CharacterDnd(String name, RaceDnd raceDnd) {

		this.name = name;
		this.raceDnd = raceDnd;
		this.classDnd = ClassDnd.randomClass();
		bag = new Bag();
		id++;
		setProfisiency();
		mySpells = new HashMap<>();
		mySpells.putAll(raceDnd.getSkillsRace());
		mySpells.putAll(classDnd.getSkillsClass());
	}
	// Race random creating
	public CharacterDnd(String name, ClassDnd classDnd) {

		this.name = name;
		this.raceDnd = RaceDnd.randomRace();
		this.classDnd = classDnd;
		bag = new Bag();
		id++;
		setProfisiency();
		mySpells = new HashMap<>();
		mySpells.putAll(raceDnd.getSkillsRace());
		mySpells.putAll(classDnd.getSkillsClass());
	}
	// Full random creating
	public CharacterDnd(String name) {
		this.name = name;
		this.raceDnd = RaceDnd.randomRace();
		this.classDnd = ClassDnd.randomClass();
		bag = new Bag();
		id++;
		setProfisiency();
		mySpells = new HashMap<>();
		mySpells.putAll(raceDnd.getSkillsRace());
		mySpells.putAll(classDnd.getSkillsClass());
		setRandomStats();
	}
	// For load 
	public CharacterDnd() {
		System.out.println("Load some character");

	}



	public void setStats(int str,int dex, int agl, int intl,int wis,int cha) {

		statsValues[0] = raceDnd.getStr(str);
		statsValues[1] = raceDnd.getDex(dex);
		statsValues[2] = raceDnd.getAgl(agl);
		statsValues[3] = raceDnd.getIntl(intl);
		statsValues[4] = raceDnd.getWis(wis);
		statsValues[5] = raceDnd.getCha(cha);

		for(int i = 0; i < statsName.length; i++) {
			statsName[i] = statsName[i] +  statsValues[i]+"("+(statsValues[i] - 10)/2+")";
		}

		setHp();

		bag.setCarryingWeight();

	}
	//Random states 
	public void setRandomStats() {

		statsValues[0] = raceDnd.getStr((int) Math.round(Math.random()*19+1));
		statsValues[1] = raceDnd.getDex((int) Math.round(Math.random()*19+1));
		statsValues[2] = raceDnd.getAgl((int) Math.round(Math.random()*19+1));
		statsValues[3] = raceDnd.getIntl((int) Math.round(Math.random()*19+1));
		statsValues[4] = raceDnd.getWis((int) Math.round(Math.random()*19+1));
		statsValues[5] = raceDnd.getCha((int) Math.round(Math.random()*19+1));

		for(int i = 0; i < statsName.length; i++) {
			statsName[i] = statsName[i] +  statsValues[i]+"("+(statsValues[i] - 10)/2+")";
		}

		setHp();

		bag.setCarryingWeight();

	}


	public ClassDnd getClassDnd() {
		return classDnd;
	}
	
	


	public int setHp() {

		int result = classDnd.getHits()+statsValues[1];
		for(int i=1; i<classDnd.getLvl(); i++) {
			result += Math.round(Math.random()*(classDnd.getDiceHits()-1) + 1);

		}

		return hp = result;
	}
	public int getHp() {
		return hp;
	}



	public void setProfisiency() {

		int result;

		if(classDnd.getLvl()>16) {
			result = 4;
		} else if(classDnd.getLvl()>9) {
			result = 3;
		} else result = 2;

		this.profisiency = result;

	}
	public int getProfisiency() {
		return profisiency;
	}



	public String setNature(int x, int y) {

		return nature = natures[0][y]+" "+natures[1][y];

	}
	public String getNature() {
		return nature;
	}


	public String getName() {
		return name;
	}
	
	public Bag getBag() {
		return bag;
	}




	//Shows
	public void showStats() {
		for(int i=0;i<6;i++) {
			System.out.println(statsName[i]);
		}
	}
	

	//save and load
	public void save(CharacterDnd characterDnd, String nameFile) throws IOException {
		File file = new File(nameFile);
		file.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(nameFile);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(characterDnd);
		objectOutputStream.close();
	}
	public CharacterDnd load(String nameFile) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(nameFile);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		CharacterDnd loaded = (CharacterDnd) objectInputStream.readObject();
		objectInputStream.close();
		return loaded;
	}


	public class Bag{


		private int carryingWeight;
		private List<Items> insideBag;


		public Bag() {
			insideBag = new ArrayList<Items>();
		}


		public int getCarryingWeight() {
			return carryingWeight;
		}


		public void setCarryingWeight() {		
			this.carryingWeight = (((statsValues[1] + statsValues[2])/4) * ((raceDnd.getWeight()*10+2)/(raceDnd.getGrowth()+1)))/10;
		}


		public int freeWeigth() {
			int occupied = 0;
			for(int i = 0; i < getInsideBag().size(); i++) {
				occupied += getInsideBag().get(i).getWeigth();
			}
			return getCarryingWeight() - occupied;
		}


		public void overloaded() {
			if(freeWeigth()<0) System.out.println("You are overloaded on " + freeWeigth() + " kg");
		}

		public void whatInTheBag() {
			if(getInsideBag().size() == 0) {
				System.out.println("Your bag are empty!");
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
			CharacterDnd characterDnd = (CharacterDnd) obj;
			return id == characterDnd.id && (name == characterDnd.name ||(name != null && name.equals(characterDnd.getName())));
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







