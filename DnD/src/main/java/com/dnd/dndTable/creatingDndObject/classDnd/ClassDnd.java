package com.dnd.dndTable.creatingDndObject.classDnd;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.KeyWallet;
import com.dnd.Log;
import com.dnd.Source;
import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.workmanship.Feature;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.factory.InerComand;
import com.dnd.dndTable.factory.Json;
import com.dnd.dndTable.factory.Script;
import com.dnd.dndTable.rolls.Dice.Roll;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;




public abstract class ClassDnd implements Serializable,ObjectDnd, Script, Source{

	private static final long serialVersionUID = 3219669745475635442L;

	private String myArchetypeClass;
	@JsonIgnore
	private int lvl;
	private Roll diceHp;


	private List<List<InerComand>> growMap;


	//Standard creating 
	public ClassDnd(int lvl) {		
		this.lvl = lvl;
	}

	public ClassDnd() 
	{		

	}

	@JsonIgnore
	public abstract int getFirstHp(); 
	@JsonIgnore
	public abstract List<String> getPermanentBuffs();

	public String getMyArchetypeClass() 
	{
		return myArchetypeClass;
	}

	public int getLvl() 
	{
		return lvl;
	}

	public void setLvl(int lvl) 
	{
		this.lvl = lvl;
	}

	public List<List<InerComand>> getGrowMap() 
	{
		return growMap;
	}

	public void setGrowMap(List<List<InerComand>> growMap) 
	{
		this.growMap = growMap;
	}

	public void setMyArchetypeClass(String myArchetypeClass) 
	{
		this.myArchetypeClass = myArchetypeClass;
	}

	public static void main(String[] args) throws JsonProcessingException 
	{

		ClassDnd assasin = new Rogue();

		assasin.diceHp = Roll.D8;
		List<List<InerComand>> pool = new ArrayList<>();

		List<InerComand> lvl0= new ArrayList<>();

		InerComand c1 = new InerComand(false, false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Light Armor");
		lvl0.add(c1);

		c1 = new InerComand(false, false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Simple Weapon");

		lvl0.add(c1);
		c1 = new InerComand(false, false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Hand Crossbows");

		lvl0.add(c1);
		c1 = new InerComand(false, false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Long Swords");

		lvl0.add(c1);
		c1 = new InerComand(false, false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Rapiers");

		lvl0.add(c1);
		c1 = new InerComand(false, false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Short Swords");

		lvl0.add(c1);
		c1 = new InerComand(false, false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Thieves' Tools");

		lvl0.add(c1);
		c1 = new InerComand(false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("SR Dexterity");

		lvl0.add(c1);
		c1 = new InerComand(false, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("SR Intelligense");

		lvl0.add(c1);
		c1 = new InerComand(true, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add(4);
		c1.getComand().get(0).add("Choose skill");

		c1.getComand().get(1).add("Acrobatics");
		c1.getComand().get(1).add("Investigation");
		c1.getComand().get(1).add("Athletics");
		c1.getComand().get(1).add("Mindfulness");
		c1.getComand().get(1).add("Performance");
		c1.getComand().get(1).add("Intimidation");
		c1.getComand().get(1).add("Sleight of hand");
		c1.getComand().get(1).add("Deception");
		c1.getComand().get(1).add("Insight");
		c1.getComand().get(1).add("Stelth");
		c1.getComand().get(1).add("Persuasion");

		lvl0.add(c1);	

		c1 = new InerComand(true, weaponKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add(1);
		c1.getComand().get(0).add("Choose item");

		c1.getComand().get(1).add("Rapier");
		c1.getComand().get(1).add("Shortsword");

		lvl0.add(c1);

		c1 = new InerComand(true, weaponKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add(1);
		c1.getComand().get(0).add("Choose item");

		c1.getComand().get(1).add("Shortbow and 20 Arrows");
		c1.getComand().get(1).add("Shortsword");

		lvl0.add(c1);

		c1 = new InerComand(true, weaponKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add(1);
		c1.getComand().get(0).add("Choose item");

		c1.getComand().get(1).add("Dungeoneer's Pack");
		c1.getComand().get(1).add("Burglar's Pack");
		c1.getComand().get(1).add("Explorer's Pack");

		lvl0.add(c1);

		c1 = new InerComand(false, weaponKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Leather Armor");
		lvl0.add(c1);
		c1 = new InerComand(false, weaponKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Dagger");
		lvl0.add(c1);
		c1 = new InerComand(false, weaponKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Thieves' Tools");
		lvl0.add(c1);

		List<InerComand> lvl1 = new ArrayList<>();

		c1 = new InerComand(false, featureKey);
		Feature competense = new Feature("Competence");
		InerComand comandss = new InerComand(true, possessionKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Competence");
		lvl1.add(c1);

		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Sneak Attack");
		lvl1.add(c1);

		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Thieves Jargon");
		lvl1.add(c1);


		List<InerComand> lvl2 = new ArrayList<>();

		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Tricky Action");
		lvl2.add(c1);


		List<InerComand> lvl3 = new ArrayList<>();

		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Additional Holdings");
		lvl3.add(c1);
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Liquidation");
		lvl3.add(c1);
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Precise Aiming");
		lvl3.add(c1);

		List<InerComand> lvl4 = new ArrayList<>();
		List<InerComand> lvl5 = new ArrayList<>();
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Incredible Evasion");
		
		lvl5.add(c1);
		List<InerComand> lvl6 = new ArrayList<>();
		List<InerComand> lvl7 = new ArrayList<>();
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add(("Evasiveness"));
		lvl7.add(c1);
		List<InerComand> lvl8 = new ArrayList<>();

		List<InerComand> lvl9 = new ArrayList<>();

		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Penetration Master");
		lvl9.add(c1);

		List<InerComand> lvl10 = new ArrayList<>();
		List<InerComand> lvl11 = new ArrayList<>();

		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Reliable Talent");
		lvl11.add(c1);

		List<InerComand> lvl12 = new ArrayList<>();
		List<InerComand> lvl13 = new ArrayList<>();
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Impostor");
		lvl13.add(c1);
		List<InerComand> lvl14 = new ArrayList<>();
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Blind Sight");
		lvl14.add(c1);
		List<InerComand> lvl15 = new ArrayList<>();
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Slippery Mind");
		lvl15.add(c1);
		List<InerComand> lvl16 = new ArrayList<>();

		List<InerComand> lvl17 = new ArrayList<>();
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Death Blow");
		lvl17.add(c1);
		List<InerComand> lvl18 = new ArrayList<>();
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Intangibility");
		lvl18.add(c1);
		List<InerComand> lvl19 = new ArrayList<>();
		List<InerComand> lvl20 = new ArrayList<>();
		c1 = new InerComand(false, featureKey);
		c1.getComand().add(new ArrayList<>());
		c1.getComand().get(0).add("Luck");
		lvl20.add(c1);

		pool.add(lvl0);
		pool.add(lvl1);
		pool.add(lvl2);
		pool.add(lvl3);
		pool.add(lvl4);
		pool.add(lvl5);
		pool.add(lvl6);
		pool.add(lvl7);
		pool.add(lvl8);
		pool.add(lvl9);
		pool.add(lvl10);
		pool.add(lvl11);
		pool.add(lvl12);
		pool.add(lvl13);
		pool.add(lvl14);
		pool.add(lvl15);
		pool.add(lvl16);
		pool.add(lvl17);
		pool.add(lvl18);
		pool.add(lvl19);
		pool.add(lvl20);


		assasin.setGrowMap(pool);

		assasin.setMyArchetypeClass("Assasin");


		String json = Json.stingify(Json.toJson(assasin));
		System.out.println(json);
		System.out.println(Json.parse(json));

		Rogue r = Json.fromJson(Json.parse(json), Rogue.class);

		System.out.println(r);
		System.out.println(r.getGrowMap());
		System.out.println(r.getGrowMap().get(0).get(0));

		for(int i = 0; i < r.getGrowMap().size(); i++)
		{
			for(InerComand comand: r.getGrowMap().get(i))
			{

				System.out.println(comand.getComand().get(0).get(0).toString());

			}
		}
		System.out.println(Json.parse(r.getGrowMap().get(0).get(0).toString()));
		String jj = r.getGrowMap().get(0).get(0).toString();
		System.out.println(jj);
		Possession pp = Json.fromJson(Json.parse(jj), Possession.class);
		System.out.println(pp);
	}

	public Roll getDiceHp() {
		return diceHp;
	}

	public void setDiceHp(Roll diceHp) {
		this.diceHp = diceHp;
	}
}


