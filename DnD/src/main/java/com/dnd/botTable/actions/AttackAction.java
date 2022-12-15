package com.dnd.botTable.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.dnd.Names.Stat;
import com.dnd.dndTable.rolls.AttackModification;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class AttackAction extends RollAction
{
	
	{
		this.getBase().add(new Dice("D20", 0, Roll.D20));
	}


	private static final long serialVersionUID = 1L;
	private AttackModification attack;

	public static AttackAction create(long key, Stat depends, boolean proficiency, AttackModification attack)
	{
		AttackAction answer = new AttackAction();
		answer.key = key;
		answer.name = (attack.getName());
		answer.mainAct = true;
		answer.mediator = false;
		answer.setDepends(depends);
		answer.setProficiency(proficiency);
		answer.setAttack(attack);
		answer.getBase().addAll(attack.getAttack());
				
		return answer;
	}


	public AttackModification getAttack() {
		return attack;
	}

	public void setAttack(AttackModification attack) {
		this.attack = attack;
	}
}
