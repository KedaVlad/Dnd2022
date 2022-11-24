package com.dnd.dndTable.rolls;

import java.io.Serializable;

import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;

public class AttackModification implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String name; //heavy attack
	private WeaponProperties[] properties; // heavy, melee
	private Dice attack; // -5
	private Dice damage; // +10
	private String effects; // if he dead you can attack again


	public WeaponProperties[] getProperties() 
	{
		return properties;
	}

	public void setProperties(WeaponProperties[] properties) 
	{
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dice getAttack() {
		return attack;
	}

	public void setAttack(Dice attack) {
		this.attack = attack;
	}

	public Dice getDamage() {
		return damage;
	}

	public void setDamage(Dice damage) {
		this.damage = damage;
	}

	public String getEffects() {
		return effects;
	}

	public void setEffects(String effects) {
		this.effects = effects;
	}

	
}
