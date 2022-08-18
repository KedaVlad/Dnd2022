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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd.Dise;
import com.dnd.creatingCharacter.bagDnd.*;
import com.dnd.creatingCharacter.classDnd.*;
import com.dnd.creatingCharacter.raceDnd.*;
import com.dnd.creatingCharacter.spells.*;


public class CharacterDnd implements Dise{

	//Serialize


	//CharacterDnd basic information
	private int id = 0;
	private String name;
	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass;
	private String nature;
	private Bag bag;
	private int speed;
	

	
	
	
	

	//Game states
	private int profisiency;
	private int hp;
	private Map<String, String> mySkills;
	private Map<String, Spells> mySpells;
	private Map<String, Integer> myStats;
	private Map<String, String> possession;

	
	//for creating
	private static final String[][] natures = {{"Chaotic", "Neutral", "Lawful"},{"Evil","Neutral","Kind"}};
	private static File[] allClass;
	
	static {
		File dir1 = new File("C:\\Users\\ALTRON\\Desktop\\dnd\\classes"); 
		allClass = dir1.listFiles();
	}


	// Full creating
	public CharacterDnd(String name) {

		this.name = name;
		mySkills = new LinkedHashMap<>();
		bag = new Bag();
		id++;
		myStats = new HashMap<>();

	}
	// For load 
	public CharacterDnd() {
		System.out.println("Load some character");

	}



	public void setRaceDnd(RaceDnd raceDnd) {
		this.myRace = raceDnd;
		this.speed = raceDnd.getSpeed();
		for(int i = 0; i < raceDnd.getSkillsRace().length; i++) {
			getMySkills().put(raceDnd.getSkillsRace()[i], raceDnd.getSkillsRace()[i]);
		}
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

		myStats.put("Strength", myRace.getStr(str));
		myStats.put("Dexterity", myRace.getDex(dex));
		myStats.put("Constitution", myRace.getAgl(con));
		myStats.put("Intelligence", myRace.getIntl(intl));
		myStats.put("Wisdom", myRace.getWis(wis));
		myStats.put("Charisma", myRace.getCha(cha));
	}



	public void lvlUp() {
		myClass.setLvl(myClass.getLvl()+1);
		setClassSkills();
	}



	public void setClassSkills() {

		File classDnd = new File("C:\\Users\\ALTRON\\Desktop\\dnd\\classes\\"+myClass+"\\"+myClass.getMyArchetypeClass()+".txt");
		File skills = new File("C:\\Users\\ALTRON\\Desktop\\dnd\\skills\\Skills.txt");

		Scanner classScanner = null;
		Scanner skillsScaner = null;

		Pattern findNameSkills = Pattern.compile("[a-zA-Z]");

		try {
			classScanner = new Scanner(classDnd);
			boolean checkLvl = false;
			while(classScanner.hasNextLine()&& !checkLvl) {
				String skill = classScanner.nextLine();
				Matcher m = findNameSkills.matcher(skill);
				if(m.find()&&!mySkills.containsKey(skill)) {

					skillsScaner = new Scanner(skills);
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

		int result = myClass.getHits()+myStats.get("Constitution");
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

	public Bag getBag() {
		return bag;
	}




	//Shows
	public void showSkills() {
		for(Map.Entry<String, String> entry: mySkills.entrySet()) {
			System.out.println(entry);
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




	public static void removeInt(List list) {
		for(int i = 0; i < 20; i ++) {
			list.remove(""+i);
		}
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
