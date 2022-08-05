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
import java.util.Set;

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
	private ClassDnd multiClass;
	private String nature;
	private Bag bag;
	private String fensyStuff;
	private int speed;
	private int lvl;
	

	//Game states
	private int profisiency;
	private int hp;
	
	
	private String[][] natures = {{"Chaotic", "Neutral", "Lawful"},{"Evil","Neutral","Kind"}};
	
	private Map<String, String> mySkills;
	private Map<String, Spells> mySpells;
	private Map<String, Integer> myStats;
	
	
	
	// Full creating
	public CharacterDnd(String name, int lvl) {

		this.name = name;
		this.lvl = lvl;
		bag = new Bag();
		id++;
		myStats = new HashMap<>();
		
	}
	// For load 
	public CharacterDnd() {
		System.out.println("Load some character");
		
	}
	
	
	
	public void setRaceDnd(RaceDnd raceDnd) {
		this.raceDnd = raceDnd;
		this.speed = raceDnd.getSpeed();
		mySkills = new HashMap<>();
		for(int i = 0; i < raceDnd.getSkillsRace().length; i++) {
			getMySkills().put(raceDnd.getSkillsRace()[i], raceDnd.getSkillsRace()[i]);
		}
	}
	
	
	
	public void setClassDnd(ClassDnd classDnd) {
		this.classDnd = classDnd;
		for(int i = 0; i < this.lvl; i++) {
			for(int n = 0; n < classDnd.getLvlSkills()[i].length; n++) {
				getMySkills().put(classDnd.getLvlSkills()[i][n], classDnd.getLvlSkills()[i][n]);
			}
			
			if(getMySkills().containsKey("Spell Use")||getMySkills().containsKey("Treaty Magic")) {
				mySpells = new HashMap<>();
			}
		}
	}
	
	
	
	public void setSomeSpell(Spells spell) {
	mySpells.put(spell.getName(),spell);
	}
	
	public void setLvl(int lvl){
		if(lvl >20|| lvl <1) {

			this.lvl=1;
			System.out.println("Your lvl can`t be aboe lvl 20 or less then lvl 1. So i give you lvl " + this.lvl);


		} else {
			this.lvl=lvl;
		}
	
	}

	
	
	
	public void castSomeSpell(String nameSpell) {
		System.out.println(getMySpells().get(nameSpell));
	}
	



	public void setStats(int str,int dex, int con, int intl,int wis,int cha) {

		myStats.put("Strength", raceDnd.getStr(str));
		myStats.put("Dexterity", raceDnd.getDex(dex));
		myStats.put("Constitution", raceDnd.getAgl(con));
		myStats.put("Intelligence", raceDnd.getIntl(intl));
		myStats.put("Wisdom", raceDnd.getWis(wis));
		myStats.put("Charisma", raceDnd.getCha(cha));
	}


	public ClassDnd getClassDnd() {
		return classDnd;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getLvl() {
		return lvl;
	}

	public void setHp() {

		int result = classDnd.getHits()+myStats.get("Constitution");
		for(int i=1; i< getLvl(); i++) {
			result += Math.round(Math.random()*(classDnd.getDiceHits()-1) + 1);

		}

		this.hp = result;
	}
	
	public int getHp() {
		return hp;
	}



	public void setProfisiency() {

		int result;

		if(getLvl()>16) {
			result = 4;
		} else if(getLvl()>9) {
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
	public void showSkills() {
		//for(Map.Entry<String, String> entry: mySkills.entrySet()) {
		//	System.out.println(entry);
			Set<String> setKeys = getMySkills().keySet();
            for(String k: setKeys){
                System.out.println(getMySkills().get(k));
            }
		}
	public void showSpells() {
		//for(Map.Entry<String, Spells> entry: mySpells.entrySet()) {
		//System.out.println(entry.toString());
		//}
		Set<String> setKeys = getMySpells().keySet();
        for(String k: setKeys){
            System.out.println(getMySpells().get(k));
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





	public Map<String, String> getMySkills() {
		return mySkills;
	}





	public Map<String, Spells> getMySpells() {
		return mySpells;
	}





	public class Bag{


		private List<Items> insideBag;


		public Bag() {
			insideBag = new ArrayList<Items>();
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







