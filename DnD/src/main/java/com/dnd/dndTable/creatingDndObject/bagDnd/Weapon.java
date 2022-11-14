package com.dnd.dndTable.creatingDndObject.bagDnd;

import com.dnd.Dice;

public class Weapon 
{

	public enum WeaponProperties
	{
		UNIVERSAL, LUNG, THROWING, FENCING, TWO_HANDED, AMMUNITION, RELOAD, AVAILABILITY, MILITARY, LONG_RANGE, MELEE, SIMPLE, HEAVY
	}

	private WeaponProperties[] properties;
	private Dice[] damage;
	private int bonusAtack;
	private int bonusHit;

	public WeaponProperties[] getProperties() 
	{
		return properties;
	}

	public void setProperties(WeaponProperties[] properties) 
	{
		this.properties = properties;
	}

	public Dice[] getDamage() 
	{
		return damage;
	}

	public void setDamage(Dice[] damage)
	{
		this.damage = damage;
	}

	public int getBonusAtack() {
		return bonusAtack;
	}

	public void setBonusAtack(int bonusAtack) {
		this.bonusAtack = bonusAtack;
	}

	public int getBonusHit() {
		return bonusHit;
	}

	public void setBonusHit(int bonusHit) {
		this.bonusHit = bonusHit;
	}

}
