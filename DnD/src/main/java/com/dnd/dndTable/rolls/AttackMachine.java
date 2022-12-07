package com.dnd.dndTable.rolls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponType;
import com.dnd.dndTable.rolls.Dice.Roll;
import com.dnd.dndTable.rolls.actions.AttackAction;
import com.dnd.dndTable.rolls.actions.HeroAction;

public class AttackMachine implements Serializable
{


	private static final long serialVersionUID = 1L;

	private Dice noWeapon = new Dice("No weapon attack", 1, Roll.NO_ROLL);
	private Weapon targetWeapon;

	private List<AttackModification> preAttacks;
	private List<AttackModification> afterAttak;
	private List<AttackModification> preHit;
	private List<AttackModification> afterHit;

	
	private List<WeaponProperties> possession;
	private List<WeaponType> typePossession;
	private boolean warlock;
	private List<WeaponProperties> dexteritys;
	private List<Dice> buffs;

	public HeroAction getPreAction(Weapon weapon)
	{
		
		
		
		return null;
	}
	
	public HeroAction getActionTree(Weapon weapon)
	{
		HeroAction action = new HeroAction();
		action.setName(weapon.getName());
		action.setText(weapon.getName());
		action.getNextStep().addAll(getAttacks(weapon));
		
		return action;
	}
	
	private Stat chekStat(AttackModification weapon)
	{
		Stat answer = Stat.STRENGTH;
		for(WeaponProperties target: dexteritys)
		{
			if(weapon.getRequirement().contains(target))
			{
				answer = Stat.DEXTERITY;
				break;
			}
		}
		return answer;
	}
	
	private boolean checkProf(AttackModification weapon)
	{
		return getPossession().contains(WeaponProperties.MILITARY) 
				|| (weapon.getRequirement().contains(WeaponProperties.SIMPLE) && getPossession().contains(WeaponProperties.SIMPLE))
				|| typePossession.contains(weapon.getType());
		
	}
	
	private List<AttackAction> preAttacks(Weapon weapon)
	{
		List<AttackAction> answer = new ArrayList<>();
		AttackModification base = weapon.getFirstType();
		List<Dice> dice = new ArrayList<>();
		dice.add(base.getAttack());
		dice.addAll(dice);
		answer.add(AttackAction.create(chekStat(base), dice, checkProf(base)));
		answer.setName(weapon.getName());
		
		
	}
	
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
		attacks.add(new Action(targetWeapon.getName(), false, attack, hit));

		for(AttackModification elseAttack: getElseAttack())
		{
			attack.setName(elseAttack.getName());
			attack.permanentBuff.add(elseAttack.getAttack());
			hit.setName(elseAttack.getName());
			hit.permanentBuff.add(elseAttack.getDamage());
			attacks.add(new Action(elseAttack.getName(), false, attack, hit));
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
			attacks.add(new Action(targetWeapon.getSecondType().getName(),false, attack, hit));
		}

		this.attacks = attacks;
	}

	private List<AttackModification> getElseAttack(Weapon we)
	{
		List<AttackModification> answer = new ArrayList<>();
		for(AttackModification type: preAttacks)
		{
			int condition = type.getRequirement().size();
			for(WeaponProperties properties: type.getRequirement())
			{
				for(WeaponProperties need: targetWeapon.getFirstType().getRequirement())
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

	

	public void setTargetWeapon(Weapon targetWeapon) 
    {
		this.targetWeapon = targetWeapon;
		setAttacks();
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


