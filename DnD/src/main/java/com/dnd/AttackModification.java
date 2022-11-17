package com.dnd;

import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;

public class AttackModification 
{

	private String name; //heavy attack
	private WeaponProperties[] properties; // heavy, melee
	private int attack; // -5
	private int damage; // +10
	private Dice[] dice; // -
	private String effects; // if he dead you can attack again
	
	public int getDamage() 
	{
		return damage;
	}
	
	public void setDamage(int damage) 
	{
		this.damage = damage;
	}
	
	public Dice[] getDice() 
	{
		return dice;
	}
	
	public void setDice(Dice[] dice) 
	{
		this.dice = dice;
	}
	
	public String[] getEffects() 
	{
		return effects;
	}
	
	public void setEffects(String[] effects)
	{
		this.effects = effects;
	}

	public WeaponProperties[] getProperties() {
		return properties;
	}

	public void setProperties(WeaponProperties[] properties) {
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	
}
