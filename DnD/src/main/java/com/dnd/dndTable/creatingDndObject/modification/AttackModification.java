package com.dnd.dndTable.creatingDndObject.modification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.dndTable.ObjectDnd;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponType;
import com.dnd.dndTable.rolls.Dice;
import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("ATTACK_MODIFICATION")
public class AttackModification implements Serializable, ObjectDnd
{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private WeaponType type;
	private WeaponProperties[] requirement;
	private boolean postAttack;
	private boolean permanentCrit;
	private List<Dice> attack;
	private List<Dice> damage;

	public AttackModification()
	{
		attack = new ArrayList<>();
		damage = new ArrayList<>();
	}
	
	public boolean equals(Object obj) 
	{
			if(obj == this) return true;
			if(obj == null || obj.getClass() != this.getClass()) return false;
			AttackModification target = (AttackModification) obj;
			return this.name == target.name && this.requirement.equals(target.requirement);
		}
	

	public AttackModification marger(AttackModification second)
	{
		AttackModification answer = new AttackModification();
		answer.name = second.name;
		answer.requirement = this.requirement;
		answer.type = this.getType();
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

	public WeaponProperties[] getRequirement() {
		return requirement;
	}

	public void setRequirement(WeaponProperties[] properties) {
		this.requirement = properties;
	}

	public List<Dice> getDamage() {
		return damage;
	}

	public void setDamage(List<Dice> damage) {
		this.damage = damage;
	}

	public WeaponType getType() {
		return type;
	}

	public static AttackModification build()
	{
		return new AttackModification();
	}
	
	public AttackModification crit()
	{
		this.permanentCrit = true;
		return this;
	}
	
	public AttackModification name(String name)
	{
		this.name = name;
		return this;
	}
	
	public AttackModification postAttack(boolean postAttack)
	{
		this.postAttack = postAttack;
		return this;
	}
	
	public AttackModification requirement(WeaponProperties... requirment)
	{
		this.requirement = requirment;
		return this;
	}
	
	public AttackModification attack(Dice...dices)
	{
		for(Dice dice: dices)
		{
			this.attack.add(dice);
		}
		return this;
	}
	
	public AttackModification damage(Dice...dices)
	{
		for(Dice dice: dices)
		{
			this.attack.add(dice);
		}
		return this;
	}

	public boolean isPostAttack() {
		return postAttack;
	}

	public boolean isPermanentCrit() {
		return permanentCrit;
	}

	@Override
	public long key() {
		// TODO Auto-generated method stub
		return ATTACK_MODIFICATION;
	}
	
	
	
}
