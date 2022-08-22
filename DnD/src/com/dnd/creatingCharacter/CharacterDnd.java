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

import com.dnd.Dise;
import com.dnd.Source;
import com.dnd.creatingCharacter.bagDnd.*;
import com.dnd.creatingCharacter.classDnd.*;
import com.dnd.creatingCharacter.raceDnd.*;
import com.dnd.creatingCharacter.skills.*;
import com.dnd.creatingCharacter.spells.*;
import com.dnd.factory.SkillFactory;


public class CharacterDnd implements Dise,Source{

	//Serialize


	//CharacterDnd basic information
	private int id = 0;
	private String name;
	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass = null;
	private String nature;
	private int speed;







	//Game states
	private int profisiency;
	private int hp;
	private int statsFreePoints = 0;
	private Map<String, Skill> mySkills;
	private Map<String, Spells> mySpells;
	private Map<String, Integer> myStats;
	private Map<String, String> possession;
	private Map<String, Bag> myBags;


	//for creating
	private static final String[][] natures = {{"Chaotic", "Neutral", "Lawful"},{"Evil","Neutral","Kind"}};



	//File system
	private List<File> filePool;





	// Full creating
	public CharacterDnd(String name,RaceDnd raceDnd, ClassDnd classDnd) {
		this.name = name;
		myBags = new LinkedHashMap<>();
		mySkills = new LinkedHashMap<>();
		satBag("Bag");
		myStats = new LinkedHashMap<>();
		setRaceDnd(raceDnd);
		setClassDnd(classDnd);
		id++;

	}
	// For load 




	public void setRaceDnd(RaceDnd raceDnd) {
		this.myRace = raceDnd;
		//this.speed = raceDnd.getSpeed();
		//for(int i = 0; i < raceDnd.getSkillsRace().length; i++) {
		//	getMySkills().put(raceDnd.getSkillsRace()[i], raceDnd.getSkillsRace()[i]);
		//}
	}



	public void setClassDnd(ClassDnd classDnd) {
		this.myClass = classDnd;
		setClassSkills();
	}




	public void setSomeSpell(Spells spell) {
		mySpells.put(spell.getName(),spell);
	}

	public void castSomeSpell(String nameSpell) {
		System.out.println(getMySpells().get(nameSpell));
	}

	public void setStats(int str,int dex, int con, int intl,int wis,int cha) {

		myStats.put("Strength", str);
		myStats.put("Dexterity", dex);
		myStats.put("Constitution", con);
		myStats.put("Intelligence", intl);
		myStats.put("Wisdom", wis);
		myStats.put("Charisma", cha);

	}



	public void lvlUp() {
		myClass.setLvl(myClass.getLvl()+1);
		setClassSkills();
	}


	public void setClassSkills() {
		Scanner classScanner = null;
		Pattern findNameSkills = Pattern.compile("*[a-zA-Z]");
		try {
			classScanner = new Scanner(myClass.getMyClassMainFile());
			boolean checkLvl = false;
			while(classScanner.hasNextLine()&& !checkLvl) {
				String skill = classScanner.nextLine();
				Matcher m = findNameSkills.matcher(skill);
				if(m.find()&&!mySkills.containsKey(skill)) {


					mySkills.put(skill, SkillFactory.createSkill(skill));


				} else if(skill.contains(""+myClass.getLvl()+1)) {
					checkLvl = true;
				}
			} 
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {
			classScanner.close();
		}

	}
	public void setRaceSkills() {

		File classDnd = new File(RaceDnd.classSource);

		Scanner classScanner = null;

		Pattern findNameSkills = Pattern.compile("*[a-zA-Z]");

		try {
			classScanner = new Scanner(classDnd);
			boolean checkLvl = false;
			while(classScanner.hasNextLine()&& !checkLvl) {
				String skill = classScanner.nextLine();
				Matcher m = findNameSkills.matcher(skill);
				if(m.find()&&!mySkills.containsKey(skill)) {

					skillsScaner = new Scanner(skill);
					Pattern findDescriptionSkills = Pattern.compile("^"+skill+"");
					while(skillsScaner.hasNextLine()) {
						String c = skillsScaner.nextLine();

						Matcher w = findDescriptionSkills.matcher(c);
						if(w.find()) {
							mySkills.put(skill, c.replaceAll("^"+skill+" (.*)","$1"));

						}

					}			
				} else if(skill.contains(""+myClass.getLvl()+1)) {
					checkLvl = true;
				}
			} 
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {
			classScanner.close();
			skillsScaner.close();
		}

	}









	public ClassDnd getClassDnd() {
		return myClass;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getLvl() {
		return myClass.getLvl();
	}

	public void setHp() {

		int statFacktor = myStats.get("Constitution");
		int result = myClass.getDiceHits()+statFacktor;
		for(int i=1; i< getLvl(); i++) {
			result += Math.round(Math.random()*(myClass.getDiceHits()-1) + 1);

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

	public Bag getBag(String bag) {
		return myBags.get(bag);
	}





	//Shows
	public void showSkills() {
		for(Entry<String, Skill> entry: mySkills.entrySet()) {
			System.out.println(entry.getKey() + " " + ((Skill) entry).getDescription());
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



	public Map<String, Skill> getMySkills() {
		return mySkills;
	}





	public Map<String, Spells> getMySpells() {
		return mySpells;
	}


	public void satBag(String bagName) {
		if(!myBags.containsKey(bagName)) {
			myBags.put(bagName, new Bag(bagName));
		} else {
			System.out.println("U already got it");
		}
	}



	public File getFile(int num) {
		return filePool.get(num);
	}




	public void setFilePool(File ... files) {
		for(File file: files) {
		filePool.add(file);
	}
	}



	class Bag {


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








//save and load
/*public void save(CharacterDnd characterDnd, String nameFile) throws IOException {
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
}*/
