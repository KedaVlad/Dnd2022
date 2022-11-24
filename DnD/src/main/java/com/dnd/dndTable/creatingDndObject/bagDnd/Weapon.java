package com.dnd.dndTable.creatingDndObject.bagDnd;

import com.dnd.dndTable.rolls.AttackModification;
import com.dnd.dndTable.rolls.Dice;

public class Weapon extends Items
{

	private static final long serialVersionUID = 3883278765106047552L;

	public enum WeaponProperties
	{
		UNIVERSAL, LUNG, THROWING, FENCING, TWO_HANDED, AMMUNITION, RELOAD, AVAILABILITY, MILITARY, LONG_RANGE, MELEE, SIMPLE, HEAVY
	}

	private WeaponProperties[] properties;
	private Dice attack;
	private Dice damage;
	private AttackModification secondType;

	public Weapon(String name) {
		super(name);
	}

	public WeaponProperties[] getProperties() 
	{
		return properties;
	}

	public void setProperties(WeaponProperties[] properties) 
	{
		this.properties = properties;
	}

	public Dice getDamage() 
	{
		return damage;
	}

	public void setDamage(Dice damage)
	{
		this.damage = damage;
	}

	public Dice getAttack() {
		return attack;
	}

	public void setAttack(Dice attack) {
		this.attack = attack;
	}

	public AttackModification getSecondType() {
		return secondType;
	}

	public void setSecondType(AttackModification secondType) {
		this.secondType = secondType;
	}

}
