package creatingCharacter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import creatingCharacter.bagDnd.Items;
import creatingCharacter.classDnd.ClassDnd;
import creatingCharacter.raceDnd.RaceDnd;

public class Character implements Serializable{
	
	//Serializer
	private static final long serialVersionUID = 11L;
	
	//Сharacter basic information
	private int id = 0;
	private String name;
	private RaceDnd raceDnd;
	private ClassDnd classDnd;
	private String nature;
	Bag bag;
	
	//Game stats
	private int[] statsValues = new int[6];
	private int hp;
	
	//Arrays for creating
	private String[] statsName = {"Strength ","Dexterity ","Constitution ","Intelligense ","Wisdom ","Charisma "};
	private String[][] natures = {{"Chaotic", "Neutral", "Lawful"},{"Evil","Neutral","Kind"}};
	
	//Game play stuff
	private int profisiency;
	public final static int d100 = (int) Math.round(Math.random()*99+1);
	public final static int d20 = (int) Math.round(Math.random()*19+1);
	public final static int d12 = (int) Math.round(Math.random()*11+1);
	public final static int d10 = (int) Math.round(Math.random()*9+1);
	public final static int d8 = (int) Math.round(Math.random()*7+1);
	public final static int d6 = (int) Math.round(Math.random()*5+1);
	public final static int d4 = (int) Math.round(Math.random()*3+1);
	
	
	public Character(String creatorName, RaceDnd race, ClassDnd classDnd) {
		
		this.name = creatorName;
		this.raceDnd = race;
		this.classDnd = classDnd;
		bag = new Bag();
		id++;
		setProfisiency();
		
	}
	
	public Character() {

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
	
	
	
	public int setHp() {
		
		int result = classDnd.getDiceHits()+statsValues[1];
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

	
	
	
	//Shows
	 public void showStats() {
		 for(int i=0;i<6;i++) {
			 System.out.println(statsName[i]);
		 }
	 }

	
	//save and load
	public void save(Character character, String nameFile) throws IOException {
		File file = new File(nameFile);
		file.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(nameFile);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(character);
		objectOutputStream.close();
	}
	public Character load(String nameFile) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(nameFile);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		Character loaded = (Character) objectInputStream.readObject();
		objectInputStream.close();
		return loaded;
	}

	
	//equals + hashCode()
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null || obj.getClass() != this.getClass()) return false;
		Character character = (Character) obj;
		return id == character.id && (name == character.name ||(name != null && name.equals(character.getName())));
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
	    result = prime * result + ((name == null) ? 0 : name.hashCode());             
	    result = prime * result + id; 
	    return result;
	}



	public class Bag{
		
    	
    	private int carryingWeight;
    	List<Items> insideBag;
    	
    	
    	public Bag() {
    		insideBag = new ArrayList<Items>();
    	}
    	
    	
		public int getCarryingWeight() {
			return carryingWeight;
		}
		

		public void setCarryingWeight() {
			this.carryingWeight = (((statsValues[1] + statsValues[2])/4) * ((raceDnd.getWeight()*10)/raceDnd.getGrowth()))/10;
		}
		
		
		public int freeWeigth() {
			int occupied = 0;
			for(int i = 0; i < insideBag.size(); i++) {
				occupied += insideBag.get(i).getWeigth();
			}
			return getCarryingWeight() - occupied;
		}
		
		
		public void overloaded() {
			if(freeWeigth()<0) System.out.println("You are overloaded on " + freeWeigth() + " kg");
		}
		
		public void whatInTheBag() {
			if(insideBag.size() == 0) {
				System.out.println("Your bag are empty!");
				} else {
					for(int i = 0; i < insideBag.size(); i++) {
						System.out.println(insideBag.get(i));
						}
					}
			}
		
		
		}
	
	
	}


	
	
	

		
	
	
		
	
	/*public void showFullInfo() {
		
		System.out.println(name + " race "+ raceDnd +" class "+classDnd + " lvl "+ classDnd.getLvl()+ " " +getNature() );
		 showStats();
		 showHp();*/
	
	

	
	

