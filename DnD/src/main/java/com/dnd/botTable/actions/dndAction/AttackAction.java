package com.dnd.botTable.actions.dndAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.dnd.Names.Stat;
import com.dnd.dndTable.creatingDndObject.Rolls.Proficiency;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class AttackAction extends RollAction
{
	private static final long serialVersionUID = 1L;
	private AttackModification attack;

	public static AttackAction create(long key, Stat depends, Proficiency proficiency, AttackModification attack)
	{
		AttackAction answer = new AttackAction();
		answer.key = key;
		answer.setName((attack.getName()));
		answer.mainAct = true;
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
