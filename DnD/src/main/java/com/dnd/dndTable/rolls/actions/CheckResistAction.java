package com.dnd.dndTable.rolls.actions;

import java.util.List;

import com.dnd.Names.Stat;
import com.dnd.dndTable.rolls.Dice;
import com.dnd.dndTable.rolls.Dice.Roll;

public class CheckResistAction extends RollAction
{
	private static final long serialVersionUID = 1L;
	{
		this.setBase(new Dice("Base", 8, Roll.NO_ROLL));
	}
}
