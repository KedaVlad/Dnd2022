package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Dice;
import com.dnd.Dice.Roll;
import com.dnd.Names.Stat;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;

public class AttackMachine implements Serializable
{


	private static final long serialVersionUID = 1L;

	private Dice noWeapon = new Dice("No weapon attack", 1, Roll.NO_ROLL);

	private List<Attack> attacks;

	private Weapon targetWeapon;

	private List<AttackModification> typeAttacks;
	private List<WeaponProperties> possession;
	private boolean warlock;
	private List<WeaponProperties> dexteritys;
	private List<Dice> buffs;

	public AttackMachine()
	{
		typeAttacks = new ArrayList<>();
		buffs = new ArrayList<>();
		possession = new ArrayList<>();
		dexteritys = new ArrayList<>();
		dexteritys.add(WeaponProperties.THROWING);
		dexteritys.add(WeaponProperties.AMMUNITION);
		dexteritys.add(WeaponProperties.RELOAD);
		dexteritys.add(WeaponProperties.LONG_RANGE);
		dexteritys.add(WeaponProperties.FENCING);
	}

	private void setAttacks()
	{
		List<Attack> attacks = new ArrayList<>();
		Article attack = new Article("Attack", checkStat(targetWeapon.getProperties()));
		attack.proficiency = checkProf(targetWeapon.getProperties());
		attack.permanentBuff.add(targetWeapon.getAttack());
		Article hit = new Article(targetWeapon.getName(), checkStat(targetWeapon.getProperties()));
		hit.permanentBuff.add(targetWeapon.getDamage());
		hit.permanentBuff.addAll(buffs);
		attacks.add(new Attack(targetWeapon.getName(), attack, hit));

		for(AttackModification elseAttack: getElseAttack())
		{
			attack.name = elseAttack.getName();
			attack.permanentBuff.add(elseAttack.getAttack());
			hit.name = elseAttack.getName();
			hit.permanentBuff.add(elseAttack.getDamage());
			attacks.add(new Attack(elseAttack.getName(), attack, hit));
		}
		
		if(targetWeapon.getSecondType() != null)
		{
			attack.name = targetWeapon.getSecondType().getName();
			attack.depends = checkStat(targetWeapon.getSecondType().getProperties());
			attack.proficiency = checkProf(targetWeapon.getSecondType().getProperties());
			attack.permanentBuff.add(targetWeapon.getSecondType().getAttack());
			hit.name = targetWeapon.getSecondType().getName();
			hit.depends = checkStat(targetWeapon.getSecondType().getProperties());
			hit.permanentBuff.clear();
			hit.permanentBuff.add(targetWeapon.getSecondType().getDamage());
			hit.permanentBuff.addAll(buffs);
			attacks.add(new Attack(targetWeapon.getSecondType().getName(), attack, hit));
		}

		this.attacks = attacks;
	}

	private List<AttackModification> getElseAttack()
	{
		List<AttackModification> answer = new ArrayList<>();
		for(AttackModification type: typeAttacks)
		{
			int condition = type.getProperties().length;
			for(WeaponProperties properties: type.getProperties())
			{
				for(WeaponProperties need: targetWeapon.getProperties())
				{
					if(properties.equals(need))
					{
						condition--;
						break;
					}
				}
				if(condition == 0)
				{
					answer.add(type);
					break;
				}
			}
		}
		return answer;
	}
	
	private Stat checkStat(WeaponProperties[] weapon)
	{
		Stat target = Stat.STRENGTH;
		for(WeaponProperties properties: dexteritys)
		{
			for(WeaponProperties properti: weapon)
			{
				if(properties.equals(properti))
				{
					target = Stat.DEXTERITY;
					break;
				}
			}
		}
		return target;
	}

	private boolean checkProf(WeaponProperties[] weapon)
	{
		boolean target = false;
		for(WeaponProperties properties: possession)
		{
			for(WeaponProperties properti: weapon)
			{
				if(properties.equals(properti))
				{
					target = true;
					break;
				}
			}
		}
		return target;
	}

	public void setTargetWeapon(Weapon targetWeapon) 
    {
		this.targetWeapon = targetWeapon;
		setAttacks();
	}

	public List<Attack> getAttacks() 
	{
		return attacks;
	}

	public Dice getNoWeapon() {
		return noWeapon;
	}

	public void setNoWeapon(Dice noWeapon) {
		this.noWeapon = noWeapon;
	}


}

class Attack 
{

	String name;
	Article attack;
	Article hit;
	List<String> spesials;
	
	Attack(String name, Article attack, Article hit, List<String> spesials)
	{
		this.name = name;
		this.attack = attack;
		this.hit = hit;
		this.spesials = spesials;
	}
	
	Attack(String name, Article attack, Article hit)
	{
		this.name = name;
		this.attack = attack;
		this.hit = hit;
	}
	
}


