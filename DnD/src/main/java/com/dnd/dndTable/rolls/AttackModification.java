package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponType;

public class AttackModification implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String name;
	private List<WeaponProperties> properties;
	private WeaponType type;
	private Dice attack;
	private Dice damage;
	private String effects; // if he dead you can attack again



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

	public WeaponType getType() {
		return type;
	}

	public void setType(WeaponType type) {
		this.type = type;
	}

	public List<WeaponProperties> getProperties() {
		return properties;
	}

	public void setProperties(List<WeaponProperties> properties) {
		this.properties = properties;
	}

	
}
