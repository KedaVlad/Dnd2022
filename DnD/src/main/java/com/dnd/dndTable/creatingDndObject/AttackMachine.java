package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Log;
import com.dnd.Names.Stat;
import com.dnd.botTable.Action;
import com.dnd.botTable.actions.WrappAction;
import com.dnd.botTable.actions.dndAction.PreRoll;
import com.dnd.botTable.actions.dndAction.RegistrateAction;
import com.dnd.botTable.actions.dndAction.RollAction;
import com.dnd.dndTable.DndKeyWallet;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.Weapons;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession.Proficiency;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class AttackMachine implements Serializable, DndKeyWallet 
{
	private static final long serialVersionUID = 1L;
	final long key = ATTACK_MACHINE;

	private Dice noWeapon = new Dice("No weapon attack", 1, Roll.NO_ROLL);
	private int critX = 1;
	private Possessions possession;
	private List<AttackModification> preAttacks;
	private List<AttackModification> afterAttak;
	private List<AttackModification> permanent;
	private Weapon targetWeapon;
	private AttackModification targetAttack;
	//private boolean warlock;

	public WrappAction startAction(Weapon weapon)
	{
		Log.add("startAction in attack machine");
		targetWeapon = weapon;
		List<AttackModification> attacks = buildAttacks();
		Action[][] actions = new Action[attacks.size()][1];
		for(int i = 0; i < attacks.size(); i++)
		{
			AttackModification target = attacks.get(i);
			actions[i][0] = RegistrateAction.create(target.getName(), target).key(ATTACK_MACHINE);
		}
		return WrappAction.create(weapon.getName(), key, weapon.getDescription(), actions);
	}
	
	private List<AttackModification> buildAttacks()
	{
		List<AttackModification> answer = new ArrayList<>();
		AttackModification base = AttackModification.build().name("Base attack")
				.attack(new Dice("Weapon buff", targetWeapon.getAttack(), Roll.NO_ROLL))
				.damage(new Dice("Weapon buff", targetWeapon.getDamage(), Roll.NO_ROLL));
		for(AttackModification type: targetWeapon.getAttacksTypes())
		{
			AttackModification target = type.marger(base);
			target = permanentBuff(target);
			answer.addAll(buildSubAttacks(target , preAttacks));
			
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

	private List<AttackModification> buildSubAttacks(AttackModification attack, List<AttackModification> attacks)
	{
		List<AttackModification> answer = new ArrayList<>();

		Log.add("---------- buildSubAttacks SIZE TARGET " + attack.getRequirement().length);
		answer.add(attack);
		for(AttackModification type: attacks)
		{
			Log.add("---------- buildSubAttacks SIZE TYPE " +  type.getRequirement().length);
			int condition = type.getRequirement().length;
			String typeAttak = "|";
			for(WeaponProperties properties: type.getRequirement())
			{
				typeAttak += properties.toString() + "|";
				for(WeaponProperties need: attack.getRequirement())
				{
					if(properties.equals(need))
					{
						condition--;
						break;
					}
				}
			}
			if(condition == 0)
			{
				AttackModification compleat = attack.marger(type);
				compleat.setName(type.getName() + typeAttak);
				answer.add(compleat);
			}
		}

		return answer;
	}

	RollAction makeAttack(AttackModification attack) 
	{
		targetAttack = attack;
		return RollAction.create(attack.getName(), attack.getAttack(), key, null, attack.getStatDepend(), buildProf());
	}
	
	private Proficiency buildProf()
	{

		if(possession.holded(WeaponProperties.MILITARY.toString())
				|| simpleCheck() && possession.holded(WeaponProperties.SIMPLE.toString())
				|| possession.holded(targetWeapon.getType().toString()))
		{
			return Proficiency.BASE;
		}
		return null;
	}
	
	private boolean simpleCheck()
	{
		for(WeaponProperties properties: targetWeapon.getAttacksTypes()[0].getRequirement())
		{
			if(properties.equals(WeaponProperties.SIMPLE)) return true;
		}
		return false;
	}
	
 	WrappAction postAttack(PreRoll attack)
	{
		if(attack.isCriticalMiss())
		{
			String text = attack.getText() + "\n\nNATURAL 1!!! GOODLUCK NEXT TIME";
			return WrappAction.create(attack.getName(), key, text, null);
		}
		else if(attack.isCriticalHit())
		{
			return makeCrit(targetAttack, attack.getText());
		}
		else
		{
			return makeHit(targetAttack, attack.getText());
		}
	}

	private WrappAction makeHit(AttackModification attack, String text) 
	{
		List<AttackModification> attacks = buildSubAttacks(attack, afterAttak);
		Action[][] nextSteps = new Action[attacks.size()+1][1];
		for(int i = 0; i < attacks.size(); i++)
		{
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.create(target.getName(), target.getDamage(), key, null, attack.getStatDepend(), null);
		}
		nextSteps[nextSteps.length - 1][0] = WrappAction.create("MISS", key, "GOODLUCK NEXT TIME", null);
		return WrappAction.create("Hit", key, text + "\nDid you hit?", nextSteps);
	}

	WrappAction makeCrit(AttackModification attack, String text) 
	{
		List<AttackModification> attacks = buildSubAttacks(crit(attack), afterAttak);
		Action[][] nextSteps = new Action[attacks.size()+1][1];
		for(int i = 0; i < attacks.size(); i++)
		{
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.create(target.getName(), target.getDamage(),key, null, attack.getStatDepend(), null);
		}
		return WrappAction.create(attack.getName(), key, text, nextSteps);
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

	public AttackMachine(Possessions possession)
	{
		this.possession = possession;
		preAttacks = new ArrayList<>();
		afterAttak = new ArrayList<>();
		permanent = new ArrayList<>();
	}

	public Dice getNoWeapon() {
		return noWeapon;
	}

	public void setNoWeapon(Dice noWeapon) {
		this.noWeapon = noWeapon;
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


