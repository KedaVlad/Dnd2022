package com.dnd;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dnd.Dice.Formula;
import com.dnd.Dice.Roll;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon;
import com.dnd.dndTable.creatingDndObject.bagDnd.Weapon.WeaponProperties;

public class Attack
{

	private Dice noWeapon = new Dice("No weapon attack", 1, Roll.NO_ROLL);
	
	private Weapon targetWeapon;
	
	private List<Map<WeaponProperties, AttackModification>> typeAttacks = new ArrayList<>();
	
	private List<AttackModification> buffs = new ArrayList<>();
	
	private List<TargetAttack> targetAttacks = new ArrayList<>();
	
	private TargetAttack targetHit;
	
	private List<String> heand = new ArrayList<>();
	
	private List
	
	public String attack()
	{
		int finalResult = targetWeapon.getBonusAtack() + Dice.diceRoll(targetWeapon.getDamage());
		String answer = targetWeapon.getName() +  targetWeapon.getBonusAtack() + Dice.execute(targetWeapon.getDamage());
		
		
		return answer + "\nResult " + finalResult;
	}
	
	public String hit()
	{
		int finalResult = targetWeapon.getBonusHit() + Dice.diceRoll(targetWeapon.getDamage());
		String answer = targetWeapon.getName()+ Dice.execute(targetWeapon.getDamage(), targetWeapon.getBonusHit());
		
		
		return answer + "\nResult " + finalResult;
	}

	public void setTargetWeapon(Weapon targetWeapon) {
		this.targetWeapon = targetWeapon;
	}
	
	
	
	
}


class TargetAttack
{
	
	String name;
	boolean prof;
	

	
	TargetAttack(Weapon weapon)
	{
		
	}
}


