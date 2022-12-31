package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.dndAction.AttackAction;
import com.dnd.botTable.actions.dndAction.HeroAction;
import com.dnd.botTable.actions.dndAction.PreRoll;
import com.dnd.botTable.actions.dndAction.RollAction;
import com.dnd.dndTable.DndKeyWallet;
import com.dnd.dndTable.creatingDndObject.Rolls.Proficiency;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.Weapons;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class AttackMachine implements Serializable, DndKeyWallet 
{
	private static final long serialVersionUID = 1L;
    static final long key = ATTACK_MACHINE;
	
	private Dice noWeapon = new Dice("No weapon attack", 1, Roll.NO_ROLL);
	private int critX = 1;

	private List<AttackModification> preAttacks;
	private List<AttackModification> afterAttak;
	private List<AttackModification> permanent;
	private List<WeaponProperties> possession;
	private List<Weapons> typePossession;
	private List<WeaponProperties> dexteritys;
	//private boolean warlock;
	
	public HeroAction startAction(Weapon weapon)
	{
		List<AttackModification> attacks = buildAttacks(weapon);
		Action[][] actions = new Action[attacks.size()][0];
		for(int i = 0; i < attacks.size(); i++)
		{
			AttackModification target = attacks.get(i);
			actions[i][0] = AttackAction.create(key, checkStat(target), checkProf(weapon), target);
		}
		return HeroAction.create(weapon.getName(), key, weapon.getName(), actions);
	}
	
	HeroAction postAttack(PreRoll attack)
	{
		if(attack.isCriticalMiss())
		{
			String text = attack.getText() + "\n\nNATURAL 1!!! GOODLUCK NEXT TIME";
			return HeroAction.create(attack.getName(), key, text, null);
		}
		else if(attack.isCriticalHit())
		{
			return makeCrit(attack);
		}
		else
		{
			return makeHit(attack);
		}
	}
	
	private HeroAction makeHit(PreRoll attack) 
	{
		AttackAction action = (AttackAction) attack.getAction();
		List<AttackModification> attacks = getAttacks(action.getAttack(), afterAttak);
		Action[][] nextSteps = new Action[attacks.size()][1];
		for(int i = 0; i < attacks.size(); i++)
		{
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.create(target.getName(), target.getDamage(), key, null, checkStat(target), null);
		}
		nextSteps[nextSteps.length - 1][0] = HeroAction.create("MISS", key, "GOODLUCK NEXT TIME", null);
		return HeroAction.create(attack.getName(), key, attack.getText(), nextSteps);
	}

	private HeroAction makeCrit(PreRoll attack) 
	{
		AttackAction action = (AttackAction) attack.getAction();
		List<AttackModification> attacks = getAttacks(crit(action.getAttack()), afterAttak);
		Action[][] nextSteps = new Action[attacks.size()][1];
		for(int i = 0; i < attacks.size(); i++)
		{
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.create(target.getName(), target.getDamage(),key, null, checkStat(target), null);
		}
		return HeroAction.create(attack.getName(), key, attack.getText(), nextSteps);
	}

	private AttackModification crit(AttackModification attack) 
	{
		Dice damage = attack.getDamage().get(0);
		Roll roll = damage.getCombo()[0];
		List<Roll> critsRolls = new ArrayList<>();
		for(int i = 0; i < critX; i++)
		{
			critsRolls.add(roll);
		}
		attack.getDamage().add(new Dice("Crit", 0, (Roll[])critsRolls.toArray()));
		return attack;
	}

	private Stat checkStat(AttackModification weapon)
	{
		Stat answer = Stat.STRENGTH;
		for(WeaponProperties target: dexteritys)
		{
			if(require(weapon.getRequirement(), target))
			{
				answer = Stat.DEXTERITY;
				break;
			}
		}
		return answer;
	}

	private Proficiency checkProf(Weapon weapon)
	{
		
		if(getPossession().contains(WeaponProperties.MILITARY) 
				|| (require(weapon.getFirstType().getRequirement(),WeaponProperties.SIMPLE) && getPossession().contains(WeaponProperties.SIMPLE))
				|| typePossession.contains(weapon.getType()))
		{
			return Proficiency.BASE;
		}
		return null;
	}
	
	private boolean require(WeaponProperties[] pool, WeaponProperties target)
	{
		for(WeaponProperties properties: pool)
		{
			if(properties.equals(target)) return true;
		}
		return false;
	}

	private List<AttackModification> buildAttacks(Weapon weapon)
	{
		List<AttackModification> answer = new ArrayList<>();
		AttackModification base = weapon.getFirstType();
		base.getAttack().get(0).setBuff(weapon.getAttack());
		base.getDamage().get(0).setBuff(weapon.getDamage());
		base = permanentBuff(base);
		answer.addAll(getAttacks(base, preAttacks));

		if(weapon.getSecondType() != null)
		{
			AttackModification second = weapon.getSecondType();
			second.getAttack().get(0).setBuff(weapon.getAttack());
			second.getDamage().get(0).setBuff(weapon.getDamage());
			second = permanentBuff(second);
			answer.addAll(getAttacks(second, preAttacks));
		}

		return answer;

	}
	
	private AttackModification permanentBuff(AttackModification attack)
	{
		for(AttackModification type: permanent)
		{
			int condition = type.getRequirement().length;
			for(WeaponProperties properties: type.getRequirement())
			{
				if(condition == 0)
				{
					attack = attack.marger(type);
					break;
				}
				else
				{
					for(WeaponProperties need: attack.getRequirement())
					{
						if(properties.equals(need))
						{
							condition--;
							break;
						}
					}
				}
			}
		}

		return attack;
	}

	private List<AttackModification> getAttacks(AttackModification attack, List<AttackModification> attacks)
	{
		List<AttackModification> answer = new ArrayList<>();
		answer.add(attack);
		for(AttackModification type: attacks)
		{
			int condition = type.getRequirement().length;
			for(WeaponProperties properties: type.getRequirement())
			{
				if(condition == 0)
				{
					answer.add(attack.marger(type));
					break;
				}
				else
				{
					for(WeaponProperties need: attack.getRequirement())
					{
						if(properties.equals(need))
						{
							condition--;
							break;
						}
					}
				}
			}
		}
		return answer;
	}

	public AttackMachine()
	{

		preAttacks = new ArrayList<>();
		afterAttak = new ArrayList<>();
		permanent = new ArrayList<>();
		possession = new ArrayList<>();
		typePossession = new ArrayList<>();
		dexteritys = new ArrayList<>();
		dexteritys.add(WeaponProperties.THROWING);
		dexteritys.add(WeaponProperties.AMMUNITION);
		dexteritys.add(WeaponProperties.RELOAD);
		dexteritys.add(WeaponProperties.LONG_RANGE);
		dexteritys.add(WeaponProperties.FENCING);
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

	public List<Weapons> getTypePossession() {
		return typePossession;
	}

	public void setTypePossession(Weapons possession) {
		this.typePossession.add(possession);
	}

	public List<AttackModification> getAfterAttak() {
		return afterAttak;
	}

	public void setAfterAttak(List<AttackModification> afterAttak) {
		this.afterAttak = afterAttak;
	}

	public List<AttackModification> getPreAttacks() {
		return preAttacks;
	}

	public void setPreAttacks(List<AttackModification> preAttacks) {
		this.preAttacks = preAttacks;
	}

	public List<AttackModification> getPermanent() {
		return permanent;
	}

	public void setPermanent(List<AttackModification> permanent) {
		this.permanent = permanent;
	}

}


