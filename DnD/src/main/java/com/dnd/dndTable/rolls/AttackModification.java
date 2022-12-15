package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.List;

import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponType;

public class AttackModification implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String name;
	private List<WeaponProperties> requirement;
	private WeaponType type;
	private List<Dice> attack;
	private List<Dice> damage;


	public AttackModification marger(AttackModification second)
	{
		AttackModification answer = new AttackModification();
		answer.name = second.name;
		answer.requirement = this.requirement;
		answer.type = this.type;
		answer.attack = this.attack;
		answer.attack.addAll(second.attack);
		answer.damage = this.damage;
		answer.damage.addAll(second.damage);
	
		return answer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Dice> getAttack() {
		return attack;
	}

	public void setAttack(List<Dice> attack) {
		this.attack = attack;
	}

	public WeaponType getType() {
		return type;
	}

	public void setType(WeaponType type) {
		this.type = type;
	}

	
	public List<WeaponProperties> getRequirement() {
		return requirement;
	}

	public void setRequirement(List<WeaponProperties> properties) {
		this.requirement = properties;
	}

	public List<Dice> getDamage() {
		return damage;
	}

	public void setDamage(List<Dice> damage) {
		this.damage = damage;
	}

	
}
