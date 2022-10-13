package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;
import com.dnd.dndTable.creatingDndObject.skills.Possession;
import com.dnd.dndTable.factory.WorkmanshipFactory;

public class CharacterDnd implements Serializable, ObjectDnd
{

	private static final long serialVersionUID = -7781627593661723428L;

	private String name;
	private int lvl;
	private int hp;
	private String nature;
	private int speed;
	private boolean multiClassStatus = false;

	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass;
	private Stats myStats;
	private Workmanship myWorkmanship;
	private Bag myBag;

	private List<String> myMemoirs;


	public CharacterDnd(String name) 
	{
		this.name = name;
		myWorkmanship = new Workmanship();
		myMemoirs = new ArrayList<>();	


	}

	public void lvlUp(CharacterDnd character) 
	{
		myClass.setLvl(myClass.getLvl()+1);
		WorkmanshipFactory.getWorkmanship(character, WorkmanshipFactory.getWorkmanshipClass(myClass));
	}

	public ClassDnd getClassDnd() 
	{
		return myClass;
	}

	public int getLvl() 
	{
		return lvl;
	}

	public String getName() 
	{
		return name;
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
		multiClassStatus = true;
	}

	public void setRaceDnd(RaceDnd raceDnd) 
	{
		this.myRace = raceDnd;
	}

	public void setClassDnd(ClassDnd classDnd) 
	{
		this.myClass = classDnd;
		setLvl();
	}

	public String getMyMemoirs() 
	{

		String answer = "";
		for(String string: myMemoirs)
		{
			answer += string + "\n";
		}

		return answer;
	}

	public void setMyMemoirs(String memoirs) 
	{

		myMemoirs.add(memoirs);
	}

	public String getMenu() 
	{

		return name;
	}

	public void setLvl() {

		if(multiClassStatus == true)
		{
			lvl = myClass.getLvl() + multiClass.getLvl();
		}
		else
		{
			lvl = myClass.getLvl();
		}
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	private void setProfisiency() {

		int result = 0;

		if(lvl>16) 
		{
			result = 5;
		} 
		else if(lvl>9) 
		{
			result = 4;
		} 
		else if(lvl>4) 
		{
			result = 3;
		} 
		else
		{
			result = 2;
		}
       if(result != myWorkmanship.getProfisiency())
       {
    	   myWorkmanship.setProfisiency(result);
    	   myWorkmanship.giveProfisiencyToStats(myStats);
       }
       
	}
	
 	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Stats getMyStat() {
		return myStats;
	}

	public void setMyStat(int str, int dex, int con, int intl, int wis, int cha) {
		myStats = new Stats(str, dex, con, intl, wis, cha);
		setProfisiency();
		
	}

	public Workmanship getWorkmanship() {
		return myWorkmanship;
	}

	public Bag getMyBag() {
		return myBag;
	}

	public void setMyBag(Bag myBag) {
		this.myBag = myBag;
	}


}
