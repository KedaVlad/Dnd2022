package com.dnd.dndTable.creatingDndObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.Executor;
import com.dnd.KeyWallet;
import com.dnd.botTable.Act;
import com.dnd.botTable.actions.Action;
import com.dnd.botTable.actions.BaseAction;
import com.dnd.botTable.actions.PoolActions;
import com.dnd.botTable.actions.PreRoll;
import com.dnd.botTable.actions.RollAction;
import com.dnd.botTable.actions.Action.Location;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.Weapons;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
import com.dnd.dndTable.creatingDndObject.workmanship.Possessions;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession.Proficiency;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class AttackMachine implements Executor
{
	private static final long serialVersionUID = 1L;

	private Dice noWeapon = new Dice("No weapon attack", 1, Roll.NO_ROLL);
	private int critX = 1;
	private Possessions possession;
	private List<AttackModification> preAttacks;
	private List<AttackModification> afterAttak;
	private List<AttackModification> permanent;
	private Weapon targetWeapon;
	private AttackModification targetAttack;
	//private boolean warlock;

	@Override
	public Act execute(Action action) 
	{
		if(action.getObjectDnd() == null)
		{
			int condition = Executor.condition(action);
			if(condition == 0)
			{
				return Act.builder().name("MISS").text("GOODLUCK NEXT TIME").build();
			}
			else
			{
				return postAttack(action);
			}

		}
		else
		{
			if(action.getObjectDnd() instanceof Weapon)
			{
				return startAttack((Weapon) action.getObjectDnd());
			}
			else if(action.getObjectDnd() instanceof AttackModification)
			{
				AttackModification target = (AttackModification) action.getObjectDnd();
				return makeAttack(target);
				
			}
			else
			{
				return Act.builder().returnTo(MENU_B, MENU_B).build();
			}
		}
	}

	private Act startAttack(Weapon weapon)
	{
		targetWeapon = weapon;
		List<AttackModification> attacks = buildAttacks();
		BaseAction[][] pool = new BaseAction[attacks.size()][1];
		for(int i = 0; i < attacks.size(); i++)
		{
			AttackModification target = attacks.get(i);
			pool[i][0] = Action.builder().location(Location.CHARACTER).name(target.getName()).key(ATTACK_MACHINE).objectDnd(target).build();
		}
		return Act.builder()
				.name(weapon.getName())
				.text(weapon.getDescription())
				.action(PoolActions.builder()
						.actionsPool(pool)
						.build())
				.build();
	}

	private List<AttackModification> buildAttacks()
	{
		List<AttackModification> answer = new ArrayList<>();
		AttackModification base = AttackModification.build().name("Base attack");
				if(targetWeapon.getAttack() > 0)
				{
					base.attack(new Dice("Weapon buff", targetWeapon.getAttack(), Roll.NO_ROLL));
				}
				if(targetWeapon.getDamage() > 0)
				{					
					base.damage(new Dice("Weapon buff", targetWeapon.getDamage(), Roll.NO_ROLL));
				}
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
		answer.add(attack);
		for(AttackModification type: attacks)
		{
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

	private Act makeAttack(AttackModification attack) 
	{
		targetAttack = attack;
		return Act.builder()
				.name(attack.getName())
				.text(attack.toString())
				.action(PreRoll.builder()
						.key(key())
						.roll(RollAction.buider()								
								.diceCombo(attack.getAttack().toArray(Dice[]::new))
								.proficiency(buildProf())
								.statDepend(attack.getStatDepend())
								.build())
						.build())
				.build();
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

	private Act postAttack(Action action)
	{
		int condition = Executor.condition(action);
		if(condition == 1)
		{
			return makeHit(action);
		}
		else
		{
			return makeCrit(action);
		}
	}

	private Act makeHit(Action action) 
	{
		List<AttackModification> attacks = buildSubAttacks(targetAttack, afterAttak);
		BaseAction[][] nextSteps = new BaseAction[attacks.size()+1][1];
		for(int i = 0; i < attacks.size(); i++)
		{
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.buider()
					.name(target.getName())
					.diceCombo(target.getDamage().toArray(Dice[]::new))
					.statDepend(target.getStatDepend())
					.build();
		}
		nextSteps[nextSteps.length - 1][0] = Action.builder().name("MISS").build();
		return Act.builder()
				.name("makeHit")
				.text(action.getAnswers()[0])
				.action(PoolActions.builder().actionsPool(nextSteps).build())
				.build();
	}

	private Act makeCrit(Action action) 
	{
		String crit = action.getAnswers()[1];
		if(crit.equals(DELETE_B))
		{
			return Act.builder()
					.name("criticalMiss")
					.text(action.getAnswers()[0] + "\nGOODLUCK NEXT TIME")
					.build();
		}
		else
		{
		List<AttackModification> attacks = buildSubAttacks(crit(targetAttack), afterAttak);
		BaseAction[][] nextSteps = new BaseAction[attacks.size()][1];
		for(int i = 0; i < attacks.size(); i++)
		{
			AttackModification target = attacks.get(i);
			nextSteps[i][0] = RollAction.buider()
					.name(target.getName())
					.diceCombo((Dice[])target.getDamage().toArray())
					.statDepend(target.getStatDepend())
					.build();
		}
		
		return Act.builder()
				.name("makeCrit")
				.text(action.getAnswers()[0])
				.action(PoolActions.builder().actionsPool(nextSteps).build())
				.build();
		}
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

	@Override
	public long key() 
	{
		return ATTACK_MACHINE;
	}
}


