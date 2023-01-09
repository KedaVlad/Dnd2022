package com.dnd.botTable.actions.dndAction;


import com.dnd.Names.Stat;
import com.dnd.dndTable.creatingDndObject.modification.AttackModification;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession.Proficiency;

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
		//answer.getBase().addAll(attack.getAttack());
				
		return answer;
	}


	public AttackModification getAttack() {
		return attack;
	}

	public void setAttack(AttackModification attack) {
		this.attack = attack;
	}
}
