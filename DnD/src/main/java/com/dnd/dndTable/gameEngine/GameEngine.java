package com.dnd.dndTable.gameEngine;

import java.io.Serializable;

public class GameEngine implements Serializable {


	private static final long serialVersionUID = -5940579761014763559L;
	
	private int profisiency;
	private String nature;
	private int hp;
	private int lvl;

	private int speed;
	private Bag myBag;
	private Stats myStat;
	private MyWorkmanship myWorkmanship;

	public GameEngine() 
	{
		myBag = new Bag("Base");
		myStat = new Stats();
		myWorkmanship = new MyWorkmanship();

	}





	public void setProfisiency() 
	{

		int result = 0;

		if(lvl>16) 
		{
			result = 4;
		} 
		else if(lvl>9) 
		{
			result = 3;
		} 
		else
		{
			result = 2;
		}


		this.profisiency = result;

	}

	public int getProfisiency() 
	{
		return profisiency;
	}

	public Bag getMyBag() {
		return myBag;
	}
	public Stats getMyStat() {
		return myStat;
	}
	public MyWorkmanship getMyWorkmanship() {
		return myWorkmanship;
	}





	public String getNature() {
		return nature;
	}





	public void setNature(String nature) {
		this.nature = nature;
	}





	public int getHp() {
		return hp;
	}





	public void setHp(int hp) {
		this.hp = hp;
	}





	public int getSpeed() {
		return speed;
	}





	public void setSpeed(int speed) {
		this.speed = speed;
	}



}
