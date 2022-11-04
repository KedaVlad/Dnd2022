package com.dnd.dndTable.creatingDndObject.raceDnd;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Source;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.classDnd.ClassDnd;
import com.dnd.dndTable.creatingDndObject.classDnd.Rogue;
import com.dnd.dndTable.creatingDndObject.skills.Feature;
import com.dnd.dndTable.creatingDndObject.skills.Possession;
import com.dnd.dndTable.factory.InerComand;
import com.dnd.localData.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
public class RaceDnd implements Serializable, ObjectDnd, Source, KeyWallet{

	private static final long serialVersionUID = -7603608846317166137L;
	
	private int speed;
	private String raceName;
	private String subRace;
	private List<InerComand> specials;


	public RaceDnd(String raceName, String subRace, int speed) 
	{
		this.raceName = raceName;
		this.subRace = subRace;
		this.speed = speed;
	}
	
	public RaceDnd() {}

	public String getRaceName() 
	{
		return raceName;
	}

	public String getSubRace() 
	{
		return subRace;
	}

	public List<InerComand> getSpecials() 
	{
		return specials;
	}

	public void setSpecials(List<InerComand> specials) 
	{
		this.specials = specials;
	}

	public int getSpeed() 
	{
		return speed;
	}

	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}
	
	
	public static void main(String[] args) throws JsonProcessingException {

		RaceDnd assasin = new RaceDnd("Gnome", "Forest", 25);

		List<InerComand> pool= new ArrayList<>();

		InerComand c1 = new InerComand(false, statKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add(2);
		c1.getComand().get(0).add("Intelligence");
		pool.add(c1);
		
		c1 = new InerComand(false, statKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add(1);
		c1.getComand().get(0).add("Dexterity");
		pool.add(c1);
		
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Natural Illusion");
		pool.add(c1);
		
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Natural Illusion");
		pool.add(c1);
		
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Communication with small animals");
		pool.add(c1);

		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Dark vision");
		pool.add(c1);
		
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Dwarven cunning");
		pool.add(c1);

		assasin.setSpecials(pool);
		
		System.out.println( Json.stingify(Json.toJson(assasin)));
		
	}
}

