package com.dnd.dndTable.creatingDndObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Body;
import com.dnd.Log;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Bag;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.raceDnd.RaceDnd;


public class CharacterDnd implements Serializable, ObjectDnd
{

	private static final long serialVersionUID = -7781627593661723428L;

	private String name;
	private int lvl;
	private int hp;
	private String nature;
	private int speed;
	private RaceDnd myRace;
	private ClassDnd myClass;
	private ClassDnd multiClass;
	private Stats myStats;
	private Workmanship myWorkmanship;
	private Body body;
    private СhoiceCloud cloud;

    private List<String> permanentBuffs;
    private List<String> timesBuffs;
	private List<String> myMemoirs;


	public CharacterDnd(String name) 
	{
		this.name = name;
		myWorkmanship = new Workmanship();
		myMemoirs = new ArrayList<>();	
		cloud = new СhoiceCloud();
		myStats = new Stats();
		body = new Body();
		permanentBuffs = new ArrayList<>();
		timesBuffs = new ArrayList<>();


	}

	public void lvlUp(CharacterDnd character) 
	{
		myClass.setLvl(myClass.getLvl()+1);
		
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

	public void setLvl() 
	{

		if(multiClass != null)
		{
			lvl = myClass.getLvl() + multiClass.getLvl();
		}
		else
		{
			lvl = myClass.getLvl();
		}
	}

	public int getHp() 
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}

	public String getNature() 
	{
		return nature;
	}

	public void setNature(String nature) 
	{
		this.nature = nature;
	}

	private void setProfisiency() 
	{

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
	
 	public int getSpeed()
 	{
		return speed;
	}

	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}

	public Stats getMyStat() 
	{
		return myStats;
	}

	public void setMyStat(int str, int dex, int con, int intl, int wis, int cha) 
	{
		
		myStats.setStats(str, dex, con, intl, wis, cha); 
		setProfisiency();
		
		Log.add("Character stat" + myStats.getStats());
	}

	public Workmanship getWorkmanship() 
	{
		return myWorkmanship;
	}

	public СhoiceCloud getCloud() 
	{
		return cloud;
	}

	public static CharacterDnd create(String name)
	{
		return new CharacterDnd(name);
	}
	
	public List<String> getPermanentBuffs() {
	
		List<String> answer = permanentBuffs;
		if(multiClass != null)
		{
		answer.addAll(myClass.getPermanentBuffs());
		answer.addAll(multiClass.getPermanentBuffs());
		}
		else
		{
			answer.addAll(myClass.getPermanentBuffs());
		}
		
		
		return answer;
	}

	public List<String> getTimesBuffs() {
		return timesBuffs;
	}

	public Body getBody() {
		return body;
	}

	

}
