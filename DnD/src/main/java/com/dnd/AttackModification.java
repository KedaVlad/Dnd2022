package com.dnd;

import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;

public class AttackModification 
{

	private WeaponProperties[] properties;
	private int damage;
	private Dice[] dice;
	private String[] effects;
	
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
	
	
}
