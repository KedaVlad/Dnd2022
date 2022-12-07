package com.dnd.dndTable.rolls.actions;

import java.util.ArrayList;
import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.dndTable.rolls.AttackModification;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class AttackAction extends RollAction
{
	public AttackAction(String name, String text, List<HeroAction> nextStep, Stat depends, boolean proficiency) {
		super(name, text, nextStep, depends, proficiency);
		// TODO Auto-generated constructor stub
	}

	public AttackAction(String name, String text, List<HeroAction> nextStep, Stat depends, boolean proficiency, AttackModification attack) {
		super(name, text, nextStep, depends, proficiency);
		this.attack = attack;
		// TODO Auto-generated constructor stub
	}
	

	private static final long serialVersionUID = 1L;
	{
		this.setBase(new Dice("D20", 0, Roll.D20));
	}
	private AttackModification attack;

	public static AttackAction create(Stat depends, List<Dice> buff, boolean proficiency, AttackModification attack)
	{
		AttackAction answer = new AttackAction();

		answer.setDepends(depends);
		answer.setBuff(buff);

		answer.setProficiency(proficiency);

		return answer;
	}

	public AttackModification getAttack() {
		return attack;
	}

	public void setAttack(AttackModification attack) {
		this.attack = attack;
	}
}
