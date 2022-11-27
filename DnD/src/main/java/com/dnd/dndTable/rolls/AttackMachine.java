package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponType;
import com.dnd.dndTable.rolls.Dice.Roll;

public class AttackMachine implements Serializable
{


	private static final long serialVersionUID = 1L;

	private Dice noWeapon = new Dice("No weapon attack", 1, Roll.NO_ROLL);

	private List<Action> attacks;

	private Weapon targetWeapon;

	private List<AttackModification> typeAttacks;
	private List<WeaponProperties> possession;
	private List<WeaponType> typePossession;
	private boolean warlock;
	private List<WeaponProperties> dexteritys;
	private List<Dice> buffs;

	public AttackMachine()
	{
		typeAttacks = new ArrayList<>();
		buffs = new ArrayList<>();
		possession = new ArrayList<>();
		typePossession = new ArrayList<>();
		dexteritys = new ArrayList<>();
		dexteritys.add(WeaponProperties.THROWING);
		dexteritys.add(WeaponProperties.AMMUNITION);
		dexteritys.add(WeaponProperties.RELOAD);
		dexteritys.add(WeaponProperties.LONG_RANGE);
		dexteritys.add(WeaponProperties.FENCING);
	}

	private void setAttacks()
	{
		List<Action> attacks = new ArrayList<>();
		Article attack = new Article("Attack", checkStat((targetWeapon.getFirstType().getProperties())));
		attack.setProficiency(checkProf(targetWeapon.getFirstType()));
		attack.permanentBuff.add(targetWeapon.getFirstType().getAttack());
		Article hit = new Article(targetWeapon.getName(), attack.getDepends());
		hit.permanentBuff.add(targetWeapon.getFirstType().getDamage());
		hit.permanentBuff.addAll(buffs);
		attacks.add(new Action(targetWeapon.getName(), attack, hit));

		for(AttackModification elseAttack: getElseAttack())
		{
			attack.setName(elseAttack.getName());
			attack.permanentBuff.add(elseAttack.getAttack());
			hit.setName(elseAttack.getName());
			hit.permanentBuff.add(elseAttack.getDamage());
			attacks.add(new Action(elseAttack.getName(), attack, hit));
		}
		
		if(targetWeapon.getSecondType() != null)
		{
			attack.setName(targetWeapon.getSecondType().getName());
			attack.setDepends(checkStat(targetWeapon.getSecondType().getProperties()));
			attack.setProficiency(checkProf(targetWeapon.getSecondType()));
			attack.permanentBuff.add(targetWeapon.getSecondType().getAttack());
			hit.setName(targetWeapon.getSecondType().getName());
			hit.setDepends(checkStat(targetWeapon.getSecondType().getProperties()));
			hit.permanentBuff.clear();
			hit.permanentBuff.add(targetWeapon.getSecondType().getDamage());
			hit.permanentBuff.addAll(buffs);
			attacks.add(new Action(targetWeapon.getSecondType().getName(), attack, hit));
		}

		this.attacks = attacks;
	}

	private List<AttackModification> getElseAttack()
	{
		List<AttackModification> answer = new ArrayList<>();
		for(AttackModification type: typeAttacks)
		{
			int condition = type.getProperties().size();
			for(WeaponProperties properties: type.getProperties())
			{
				for(WeaponProperties need: targetWeapon.getFirstType().getProperties())
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
	
	private Stat checkStat(List<WeaponProperties> weapon)
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

	private boolean checkProf(AttackModification weapon)
	{
		if(getPossession().contains(WeaponProperties.MILITARY) 
				|| (weapon.getProperties().contains(WeaponProperties.SIMPLE) 
						&& getPossession().contains(WeaponProperties.SIMPLE))
				|| typePossession.contains(weapon.getType()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void setTargetWeapon(Weapon targetWeapon) 
    {
		this.targetWeapon = targetWeapon;
		setAttacks();
	}

	public List<Action> getAttacks() 
	{
		return attacks;
	}
	
	public Action getAttacks(int target) 
	{
		return attacks.get(target);
	}

	public Dice getNoWeapon() {
		return noWeapon;
	}

	public void setNoWeapon(Dice noWeapon) {
		this.noWeapon = noWeapon;
	}

	public List<WeaponProperties> getPossession() {
		return possession;
	}

	public void setPossession(WeaponProperties possession) {
		this.possession.add(possession);
	}

	public List<WeaponType> getTypePossession() {
		return typePossession;
	}

	public void setTypePossession(WeaponType possession) {
		this.typePossession.add(possession);
	}


}


