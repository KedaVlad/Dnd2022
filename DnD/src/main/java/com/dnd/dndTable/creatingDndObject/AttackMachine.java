package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.AttackAction;
import com.dnd.botTable.actions.HeroAction;
import com.dnd.botTable.actions.PreAttack;
import com.dnd.botTable.actions.RollAction;
import com.dnd.dndTable.DndKeyWallet;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponType;
import com.dnd.dndTable.rolls.AttackModification;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class AttackMachine implements Serializable, DndKeyWallet 
{
	private static final long serialVersionUID = 1L;
    static final long key = attackMachine;
	
	private Dice noWeapon = new Dice("No weapon attack", 1, Roll.NO_ROLL);
	private int critX = 1;

	private List<AttackModification> preAttacks;
	private List<AttackModification> afterAttak;
	private List<AttackModification> permanent;

	private List<WeaponProperties> possession;
	private List<WeaponType> typePossession;
	private List<WeaponProperties> dexteritys;
	//private boolean warlock;
	
	public HeroAction startAction(Weapon weapon)
	{
		List<AttackModification> target = buildAttacks(weapon);
		List<Action> attacks = new ArrayList<>();
		for(AttackModification attack: target)
		{
			attacks.add(AttackAction.create(key, checkStat(attack), checkProf(attack), attack));
		}
		
		return HeroAction.create(weapon.getName(), key, weapon.getName(), attacks);
	}
	
	HeroAction postAttack(PreAttack attack)
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
	
	private HeroAction makeHit(PreAttack attack) {
	
		List<Action> nextSteps = new ArrayList<>();
		List<AttackModification> attacks = getAttacks(attack.getAction().getAttack(), afterAttak);
		for(AttackModification target: attacks)
		{
		nextSteps.add(RollAction.create(key, target, null, checkStat(target), checkProf(target)));
		}
		nextSteps.add(HeroAction.create("MISS", key, "GOODLUCK NEXT TIME", null));
		return HeroAction.create(attack.getName(), key, attack.getText(), nextSteps);
	}

	private HeroAction makeCrit(PreAttack attack) {
		
		List<Action> nextSteps = new ArrayList<>();
		List<AttackModification> attacks = getAttacks(crit(attack.getAction().getAttack()), afterAttak);
		for(AttackModification target: attacks)
		{
		nextSteps.add(RollAction.create(key, target, null, checkStat(target), checkProf(target)));
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

	private boolean checkProf(AttackModification weapon)
	{
		return getPossession().contains(WeaponProperties.MILITARY) 
				|| (require(weapon.getRequirement(),WeaponProperties.SIMPLE) && getPossession().contains(WeaponProperties.SIMPLE))
				|| typePossession.contains(weapon.getType());

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
		AttackModification base = permanentBuff(weapon.getFirstType());
		answer.addAll(getAttacks(base, preAttacks));

		if(weapon.getSecondType() != null)
		{
			AttackModification second = permanentBuff(weapon.getSecondType());
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

	public List<WeaponType> getTypePossession() {
		return typePossession;
	}

	public void setTypePossession(WeaponType possession) {
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


